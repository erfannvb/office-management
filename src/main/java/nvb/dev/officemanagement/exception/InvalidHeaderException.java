package nvb.dev.officemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidHeaderException extends RuntimeException {

    public InvalidHeaderException() {
        super("Invalid Headers.");
    }
}
