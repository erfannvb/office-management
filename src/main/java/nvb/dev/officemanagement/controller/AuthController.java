package nvb.dev.officemanagement.controller;

import lombok.RequiredArgsConstructor;
import nvb.dev.officemanagement.model.request.AuthRequest;
import nvb.dev.officemanagement.model.request.RefreshTokenRequest;
import nvb.dev.officemanagement.model.response.JwtAuthResponse;
import nvb.dev.officemanagement.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.authenticate(authRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

}
