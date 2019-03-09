package pete.atm.simu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pete.atm.simu.entity.CashReport;
import pete.atm.simu.services.AutomaticTellerMachineService;

import java.util.List;

@RestController
@RequestMapping(AutomaticTellerMachineController.BASE_URL)
public class AutomaticTellerMachineController {

    public static final String BASE_URL = "/api/atm";

    @Autowired
    private AutomaticTellerMachineService automaticTellerMachineService;

    @GetMapping("/{amount}")
    public List<CashReport> getDispensingCashByAmount(@PathVariable String amount){
        Integer dispensingAmount = Integer.parseInt(amount);
        return automaticTellerMachineService.dispensingCash(dispensingAmount);
    }

    @GetMapping
    public List<CashReport> getAllCash(){
        return automaticTellerMachineService.getAllCashes();
    }
}
