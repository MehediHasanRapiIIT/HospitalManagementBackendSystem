package hospitalManagement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {


    private final PasswordEncoder passwordEncoder;
    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/public/**","/auth/**").permitAll()
//                        .requestMatchers("/admin/**").authenticated())
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/doctors/**").hasAnyRole("DOCTOR", "ADMIN"))
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oAuth2-> oAuth2.failureHandler(
                        (AuthenticationFailureHandler) (request, response, exception) -> {
                            log.error("OAuth2 Login failed: {}", exception.getMessage());
                        }
                )
                        .successHandler(oAuth2SuccessHandler));
//                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
