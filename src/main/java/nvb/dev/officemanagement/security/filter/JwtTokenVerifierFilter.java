package nvb.dev.officemanagement.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static nvb.dev.officemanagement.constant.SecurityConstant.BEARER;
import static nvb.dev.officemanagement.constant.SecurityConstant.SECRET_KEY;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    @Override
    @SuppressWarnings("unchecked")
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.replace(BEARER, "");

            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseSignedClaims(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();
            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(data -> new SimpleGrantedAuthority(data.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            throw new IllegalStateException("Token is not trusted.");
        }

    }
}
