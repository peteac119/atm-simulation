package pete.atm.simu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pete.atm.simu.ApplicationLog;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.model.DispensingResultReport;
import pete.atm.simu.services.AutomaticTellerMachineService;

@RestController
@RequestMapping(AutomaticTellerMachineController.BASE_URL)
public class AutomaticTellerMachineController {

    public static final String BASE_URL = "/api/atm";

    private final AutomaticTellerMachineService automaticTellerMachineService;

    @Autowired
    public AutomaticTellerMachineController(AutomaticTellerMachineService automaticTellerMachineService){
        this.automaticTellerMachineService = automaticTellerMachineService;
    }

    @GetMapping(value = "/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DispensingResultReport getDispensingCashByAmount(@PathVariable String amount) throws ATMException {
        ApplicationLog.log("Receive dispensing cash request with amount (" + amount + ").");
        Integer dispensingAmount = Integer.parseInt(amount);
        DispensingResultReport dispensingResultReport = automaticTellerMachineService.dispensingCash(dispensingAmount);
        ApplicationLog.log("Response back to user with result: " + dispensingResultReport);
        return dispensingResultReport;
    }

    @GetMapping(value = "/reset")
    public void reset() throws Exception {
        automaticTellerMachineService.reset();
    }
}
