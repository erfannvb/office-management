package nvb.dev.officemanagement.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException() {
        super("There is nothing to show.");
    }
}
