package pete.atm.simu.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AutomaticTellerMachineServiceImpl.class);

    private final AutomaticTellerMachineRepository automaticTellerMachineRepository;

    @Autowired
    public AutomaticTellerMachineServiceImpl(AutomaticTellerMachineRepository automaticTellerMachineRepository){
        this.automaticTellerMachineRepository = automaticTellerMachineRepository;
    }

    private boolean outOfRange(int dispensingAmount){
        return dispensingAmount < 20 || dispensingAmount > 30000;
    }

    private List<CashReport> findAllAvailableBankNote(){
        return automaticTellerMachineRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public DispensingResultReport dispensingCash(int dispensingAmount) throws ATMException {
        LOGGER.info("Receive dispensing cash request with amount ({})", dispensingAmount);

        if (outOfRange(dispensingAmount)){
            throw new DispensingAmountOutOfRangeException();
        }

        List<CashReport> availableCashReports = findAllAvailableBankNote();

        DispensingProcessor dispensingProcessor = DispensingProcessorSelector.getDispensingProcessor();
        List<CashReport> dispensedCashReports = dispensingProcessor.process(dispensingAmount, availableCashReports);

        DispensingResultReport dispensingResultReport = new DispensingResultReport(dispensedCashReports);
        automaticTellerMachineRepository.saveAll(availableCashReports);
        automaticTellerMachineRepository.flush();

        LOGGER.info("Response Result: {}", dispensingResultReport);
        return dispensingResultReport;
    }

    @Override
    public DispensingResultReport getAllAvailableBankNote() {
        List<CashReport> availableCashReports = findAllAvailableBankNote();
        return new DispensingResultReport(availableCashReports);
    }

    /**
     * This method is just for Web UI testing only.
     */
    @Override
    public DispensingResultReport reset() {
        new BootStrapData(this.automaticTellerMachineRepository).run((String) null);
        List<CashReport> availableCashReports = findAllAvailableBankNote();
        return new DispensingResultReport(availableCashReports);
    }

}
