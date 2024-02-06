package nvb.dev.officemanagement.security;

import nvb.dev.officemanagement.model.entity.UserEntity;

import java.util.Map;

public interface JwtService {

    String generateToken(UserEntity user);

    String generateRefreshToken(Map<String, Object> extraClaims, UserEntity user);

    String extractUsername(String token);

    boolean isTokenValid(String token, UserEntity user);

}
