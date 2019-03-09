package pete.atm.simu.exception;

public class InsufficientNotesException extends ATMException {
    public InsufficientNotesException(String message) {
        super(message);
    }
}
