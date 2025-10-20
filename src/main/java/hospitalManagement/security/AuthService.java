package hospitalManagement.security;

import hospitalManagement.dto.LoginRequestDto;
import hospitalManagement.dto.LoginResponseDto;
import hospitalManagement.dto.SignupResponseDto;
import hospitalManagement.entity.User;
import hospitalManagement.entity.type.AuthProviderType;
import hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );
        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public User signUpInternal(LoginRequestDto signupRequestDto, AuthProviderType authProviderType, String providerId) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if (user != null) {
            throw new RuntimeException("Username already exists");
        }

         user = User.builder()
                .username(signupRequestDto.getUsername())
                 .providerId(providerId)
                 .providerType(authProviderType)
                .build();
        if(authProviderType == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        return userRepository.save(user);

    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {

        User user = signUpInternal(signupRequestDto, AuthProviderType.EMAIL, null);

        return new SignupResponseDto(user.getId(), user.getUsername());

    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {

        AuthProviderType providerType = authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determinePrividerIdFromOAuth2User(oAuth2User, registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user == null && emailUser == null){
            //signup flow:
            String username = authUtil.
                    determineUsernameFromOAuth2User
                            (oAuth2User, registrationId, providerId);

            user = signUpInternal(new LoginRequestDto(username,null), providerType, providerId);

        }else if(user !=null){
            //Login flow

            if(email !=null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            throw new BadCredentialsException("User with email "+ email +" already exists. Please login using your credentials.");
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());
        return ResponseEntity.ok(loginResponseDto);

        //fetch providertype And Provider Id
        //save prividerType and providerId in user
        //if the user has an account the login
        //otherwise create an account


    }
}
