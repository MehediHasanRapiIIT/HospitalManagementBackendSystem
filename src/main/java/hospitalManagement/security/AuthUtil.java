package hospitalManagement.security;

import hospitalManagement.entity.User;
import hospitalManagement.entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;

@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claims()
                    .add("userId", user.getId().toString())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000*60*10))
                    .and()
                .signWith(getSecretKey())
                .compact();
        // 10 minutes
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public AuthProviderType getProviderTypeFromRegistrationId(String registrationId){
        return switch (registrationId.toLowerCase()){
            case "google" -> AuthProviderType.GOOGLE;
            case "github" -> AuthProviderType.GITHUB;
            case "facebook" -> AuthProviderType.FACEBOOK;
            default -> throw new RuntimeException("Unsupported provider type: " + registrationId);
        };
    }

    public String determinePrividerIdFromOAuth2User(OAuth2User oAuth2User, String registrationId){
        String providerId = switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("id").toString();

            default -> throw new RuntimeException("Unsupported provider type: " + registrationId);
        };

        if(providerId == null || providerId.isBlank()){
            log.error("Invalid provider id: " + registrationId);
            throw new RuntimeException("Invalid provider id from OAuth2User for provider: " + registrationId);
        }
        return providerId;
    }

    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId){
        String email = oAuth2User.getAttribute("email");
        if(email != null && !email.isBlank()) {
            return email;
        }
        return switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            case "github" -> oAuth2User.getAttribute("login");
            default -> throw new RuntimeException("Unsupported provider type: " + registrationId);
        };

    }
}
