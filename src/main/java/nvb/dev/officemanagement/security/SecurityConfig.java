package nvb.dev.officemanagement.security;

import lombok.RequiredArgsConstructor;
import nvb.dev.officemanagement.security.filter.JwtAuthenticationFilter;
import nvb.dev.officemanagement.security.impl.UserServiceDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static nvb.dev.officemanagement.constant.SecurityConstant.*;
import static nvb.dev.officemanagement.security.UserRole.ADMIN;
import static nvb.dev.officemanagement.security.UserRole.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserServiceDetailsImpl userServiceDetails;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(AUTH_URL).permitAll()

                        .requestMatchers(CLERK_URL).hasAnyAuthority(ADMIN.name(), USER.name())

                        .requestMatchers(HttpMethod.POST, DOC_URL).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, DOC_URL).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, DOC_URL).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, DOC_URL).hasAnyAuthority(ADMIN.name())
                        .requestMatchers(HttpMethod.GET, DOC_URL).hasAnyAuthority(ADMIN.name(), USER.name())

                        .requestMatchers(MANAGER_URL).hasAnyAuthority(ADMIN.name(), USER.name())

                        .requestMatchers(OFFICE_URL).hasAnyAuthority(ADMIN.name(), USER.name())

                        .requestMatchers(USER_URL).permitAll()

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userServiceDetails);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

}
