package nvb.dev.officemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException(String username) {
        super(String.format("Username %s already exists.", username));
    }
}
