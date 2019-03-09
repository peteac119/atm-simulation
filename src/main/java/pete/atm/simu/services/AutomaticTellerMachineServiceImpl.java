package pete.atm.simu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.model.DispensingResultReport;
import pete.atm.simu.processor.DispensingProcessor;
import pete.atm.simu.processor.DispensingProcessorSelector;
import pete.atm.simu.repositories.AutomaticTellerMachineRepository;
import java.util.List;

@Service
public class AutomaticTellerMachineServiceImpl implements AutomaticTellerMachineService {

    private final AutomaticTellerMachineRepository automaticTellerMachineRepository;

    @Autowired
    public AutomaticTellerMachineServiceImpl(AutomaticTellerMachineRepository automaticTellerMachineRepository){
        this.automaticTellerMachineRepository = automaticTellerMachineRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DispensingResultReport dispensingCash(int dispensingAmount) throws ATMException {
        List<CashReport> availableCashReports = automaticTellerMachineRepository.findAll();
        DispensingProcessor dispensingProcessor = DispensingProcessorSelector.getDispensingProcessor();
        List<CashReport> dispensedCashReports = dispensingProcessor.process(dispensingAmount, availableCashReports);
        DispensingResultReport dispensingResultReport = new DispensingResultReport(availableCashReports, dispensedCashReports);
        automaticTellerMachineRepository.saveAll(availableCashReports);
        return dispensingResultReport;
    }

    @Override
    public List<CashReport> getAllCashes() {
        return automaticTellerMachineRepository.findAll();
    }
}
