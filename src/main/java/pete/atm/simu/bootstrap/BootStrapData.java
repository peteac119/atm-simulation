package pete.atm.simu.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pete.atm.simu.entity.CashReport;
import pete.atm.simu.repositories.AutomaticTellerMachineRepository;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AutomaticTellerMachineRepository automaticTellerMachineRepository;

    @Autowired
    public BootStrapData(AutomaticTellerMachineRepository automaticTellerMachineRepository){
        this.automaticTellerMachineRepository = automaticTellerMachineRepository;
    }

    /**
     * Initial data on H2 database while the application is starting up.
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading more cash to ATM.");

        CashReport note1000 = new CashReport();
        note1000.setNoteType("1000 Note");
        note1000.setValue(1000);
        note1000.setAvailableNotes(1);

        CashReport note500 = new CashReport();
        note500.setNoteType("500 Note");
        note500.setValue(500);
        note500.setAvailableNotes(1);

        CashReport note100 = new CashReport();
        note100.setNoteType("100 Note");
        note100.setValue(100);
        note100.setAvailableNotes(1);

        CashReport note50 = new CashReport();
        note50.setNoteType("50 Note");
        note50.setValue(50);
        note50.setAvailableNotes(1);

        CashReport note20 = new CashReport();
        note20.setNoteType("20 Note");
        note20.setValue(20);
        note20.setAvailableNotes(1);

        automaticTellerMachineRepository.save(note1000);
        automaticTellerMachineRepository.save(note500);
        automaticTellerMachineRepository.save(note100);
        automaticTellerMachineRepository.save(note50);
        automaticTellerMachineRepository.save(note20);

        System.out.println("ATM has some cash now.");
    }
}
