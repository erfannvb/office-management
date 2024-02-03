package nvb.dev.officemanagement.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static nvb.dev.officemanagement.constant.SecurityConstant.*;
import static nvb.dev.officemanagement.security.UserPermission.ADMIN_WRITE;
import static nvb.dev.officemanagement.security.UserRole.ADMIN;
import static nvb.dev.officemanagement.security.UserRole.USER;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.DELETE, DOC_URL).hasAuthority(ADMIN_WRITE.getPermission())
                        .requestMatchers(HttpMethod.POST, DOC_URL).hasAuthority(ADMIN_WRITE.getPermission())
                        .requestMatchers(HttpMethod.PUT, DOC_URL).hasAuthority(ADMIN_WRITE.getPermission())
                        .requestMatchers(HttpMethod.PATCH, DOC_URL).hasAuthority(ADMIN_WRITE.getPermission())
                        .requestMatchers(HttpMethod.GET, DOC_URL).hasAnyRole(ADMIN.name(), USER.name())
                )
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("erfan")
                .password(passwordEncoder.encode("password123"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails user = User.builder()
                .username("david")
                .password(passwordEncoder.encode("pwd"))
                .authorities(USER.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

}
