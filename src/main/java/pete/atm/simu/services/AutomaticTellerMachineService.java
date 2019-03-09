package pete.atm.simu.services;

import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.model.DispensingResultReport;
import java.util.List;

public interface AutomaticTellerMachineService {
    DispensingResultReport dispensingCash(int total) throws ATMException;

    List<CashReport> getAllCashes();
}
