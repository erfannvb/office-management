package nvb.dev.officemanagement.security.manager;

import lombok.AllArgsConstructor;
import nvb.dev.officemanagement.exception.EntityNotFoundException;
import nvb.dev.officemanagement.exception.NoDataFoundException;
import nvb.dev.officemanagement.model.entity.UserEntity;
import nvb.dev.officemanagement.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AuthManager implements AuthenticationManager {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<UserEntity> optionalUser = userService.getUserByUsername(authentication.getName());
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            if (!passwordEncoder.matches(authentication.getCredentials().toString(), userEntity.getPassword())) {
                throw new BadCredentialsException("You provided an incorrect password.");
            }
            return new UsernamePasswordAuthenticationToken(authentication.getName(), userEntity.getPassword());
        } else {
            throw new NoDataFoundException();
        }
    }

}
