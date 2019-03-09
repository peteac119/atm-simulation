package pete.atm.simu.services;

import pete.atm.simu.exception.ATMException;
import pete.atm.simu.model.DispensingResultReport;

public interface AutomaticTellerMachineService {
    DispensingResultReport dispensingCash(int dispensingAmount) throws ATMException;

    void reset() throws Exception;
}
