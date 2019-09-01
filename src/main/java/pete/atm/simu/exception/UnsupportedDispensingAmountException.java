package pete.atm.simu.exception;

public class UnsupportedDispensingAmountException extends ATMException {

    private static final String ERROR_MESSAGE = "Current notes that ATM has do not support this dispensing amount.";

    public UnsupportedDispensingAmountException() {
        super(ERROR_MESSAGE);
    }
}
