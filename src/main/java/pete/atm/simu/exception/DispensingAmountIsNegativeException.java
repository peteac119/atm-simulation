package pete.atm.simu.exception;

public class DispensingAmountIsNegativeException extends ATMException {
    public DispensingAmountIsNegativeException(String message) {
        super(message);
    }
}
