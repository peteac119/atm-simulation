package pete.atm.simu.processor;

import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;
import java.util.List;

public interface DispensingProcessor {
    List<CashReport> process(int dispensingAmount, List<CashReport> availableCashReports) throws ATMException;
}
