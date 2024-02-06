package nvb.dev.officemanagement.security;

import nvb.dev.officemanagement.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String generateToken(UserDetails user);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails user);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails user);

}
