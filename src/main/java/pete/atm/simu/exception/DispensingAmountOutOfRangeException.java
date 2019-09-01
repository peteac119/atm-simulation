package pete.atm.simu.exception;

public class DispensingAmountOutOfRangeException extends ATMException {

    private static final String ERROR_MESSAGE = "Dispensing amount must be in between 20 and 30000.";

    public DispensingAmountOutOfRangeException() {
        super(ERROR_MESSAGE);
    }
}
