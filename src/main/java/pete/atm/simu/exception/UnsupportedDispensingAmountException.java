package pete.atm.simu.exception;

public class UnsupportedDispensingAmountException extends ATMException {
    public UnsupportedDispensingAmountException() {
        super("Current notes that ATM has do not support this dispensing amount.");
    }
}
