package pete.atm.simu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.model.DispensingResultReport;
import pete.atm.simu.services.AutomaticTellerMachineService;

import java.util.List;

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
        Integer dispensingAmount = Integer.parseInt(amount);
        return automaticTellerMachineService.dispensingCash(dispensingAmount);
    }

    @GetMapping
    public List<CashReport> getAllCash(){
        return automaticTellerMachineService.getAllCashes();
    }
}
