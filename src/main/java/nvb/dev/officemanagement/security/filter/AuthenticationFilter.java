package nvb.dev.officemanagement.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.security.manager.AuthManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

import static nvb.dev.officemanagement.constant.SecurityConstant.BEARER;
import static nvb.dev.officemanagement.constant.SecurityConstant.SECRET_KEY;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthManager authManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            UserEntity userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userEntity.getUsername(),
                    userEntity.getPassword()
            );

            return authManager.authenticate(authentication);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .subject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 500))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();

        response.addHeader("Authorization", BEARER + token);

    }
}
