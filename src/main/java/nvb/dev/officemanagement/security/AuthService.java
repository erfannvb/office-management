package nvb.dev.officemanagement.security;

import nvb.dev.officemanagement.model.request.AuthRequest;
import nvb.dev.officemanagement.model.request.RefreshTokenRequest;
import nvb.dev.officemanagement.model.response.JwtAuthResponse;

public interface AuthService {

    JwtAuthResponse authenticate(AuthRequest authRequest);

    JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
