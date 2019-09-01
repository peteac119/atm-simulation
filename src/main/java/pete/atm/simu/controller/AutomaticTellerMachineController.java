package pete.atm.simu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pete.atm.simu.constant.AppURL;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.model.DispensingResultReport;
import pete.atm.simu.services.AutomaticTellerMachineService;

@RestController
@RequestMapping(AppURL.BASE_URL)
public class AutomaticTellerMachineController {

    private final AutomaticTellerMachineService automaticTellerMachineService;

    @Autowired
    public AutomaticTellerMachineController(AutomaticTellerMachineService automaticTellerMachineService){
        this.automaticTellerMachineService = automaticTellerMachineService;
    }

    @GetMapping(value = AppURL.HIGH_TO_LOW_DISPOSING_CASH, produces = MediaType.APPLICATION_JSON_VALUE)
    public DispensingResultReport highToLowDispensingCashByAmount(@PathVariable String amount) throws ATMException {
        int dispensingAmount = Integer.parseInt(amount);
        return automaticTellerMachineService.dispensingCash(dispensingAmount);
    }

    @GetMapping(value = AppURL.ALL_AVAILABLE_NOTES, produces = MediaType.APPLICATION_JSON_VALUE)
    public DispensingResultReport getAllAvailableBankNote(){
        return automaticTellerMachineService.getAllAvailableBankNote();
    }

    @GetMapping(value = AppURL.RESET)
    public DispensingResultReport reset() throws Exception {
        return automaticTellerMachineService.reset();
    }
}
