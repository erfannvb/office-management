package nvb.dev.officemanagement.security.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.model.request.AuthRequest;
import nvb.dev.officemanagement.model.request.RefreshTokenRequest;
import nvb.dev.officemanagement.model.response.JwtAuthResponse;
import nvb.dev.officemanagement.repository.UserRepository;
import nvb.dev.officemanagement.security.JwtService;
import nvb.dev.officemanagement.security.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public JwtAuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword()));

        UserEntity user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow();

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(token);
        jwtAuthResponse.setRefreshToken(refreshToken);

        return jwtAuthResponse;
    }

    @Override
    public JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {

            String token = jwtService.generateToken(user);

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setToken(token);
            jwtAuthResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthResponse;

        }
        return null;
    }
}
