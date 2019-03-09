package pete.atm.simu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pete.atm.simu.bootstrap.BootStrapData;
import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.exception.DispensingAmountOutOfRangeException;
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ATMException.class)
    public DispensingResultReport dispensingCash(int dispensingAmount) throws ATMException {
        if (dispensingAmount < 20 || dispensingAmount > 30000){
            throw new DispensingAmountOutOfRangeException();
        }

        List<CashReport> availableCashReports = automaticTellerMachineRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));

        DispensingProcessor dispensingProcessor = DispensingProcessorSelector.getDispensingProcessor();
        List<CashReport> dispensedCashReports = dispensingProcessor.process(dispensingAmount, availableCashReports);

        DispensingResultReport dispensingResultReport = new DispensingResultReport(availableCashReports, dispensedCashReports);
        automaticTellerMachineRepository.saveAll(availableCashReports);
        automaticTellerMachineRepository.flush();
        return dispensingResultReport;
    }

    @Override
    public DispensingResultReport getAllAvaiableBankNote() {
        List<CashReport> availableCashReports = automaticTellerMachineRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));
        return new DispensingResultReport(availableCashReports, null);
    }

    /**
     * This method is just for Web UI testing only.
     *
     * @throws Exception
     */
    @Override
    public DispensingResultReport reset() throws Exception {
        new BootStrapData(this.automaticTellerMachineRepository).run(null);
        List<CashReport> availableCashReports = automaticTellerMachineRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));
        return new DispensingResultReport(availableCashReports, null);
    }

}
