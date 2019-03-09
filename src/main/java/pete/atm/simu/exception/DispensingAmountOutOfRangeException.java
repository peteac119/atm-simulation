package pete.atm.simu.exception;

public class DispensingAmountOutOfRangeException extends ATMException {
    public DispensingAmountOutOfRangeException() {
        super("Dispensing amount must be in between 20 and 30000.");
    }
}
