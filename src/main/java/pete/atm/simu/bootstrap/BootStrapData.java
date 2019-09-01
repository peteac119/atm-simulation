package pete.atm.simu.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pete.atm.simu.entity.CashReport;
import pete.atm.simu.repositories.AutomaticTellerMachineRepository;

import java.util.Arrays;

/**
 * Runner class to populate testing data for the application on live environment.
 */
@Component
@Profile("!test")
public class BootStrapData implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BootStrapData.class);

    private final AutomaticTellerMachineRepository automaticTellerMachineRepository;

    @Autowired
    public BootStrapData(AutomaticTellerMachineRepository automaticTellerMachineRepository){
        this.automaticTellerMachineRepository = automaticTellerMachineRepository;
    }

    /**
     * Initial data on H2 database while the application is starting up.
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        LOGGER.info("Start inserting cash report.");

        automaticTellerMachineRepository.deleteAll();

        CashReport note1000 = new CashReport();
        note1000.setNoteType("1000 Note");
        note1000.setValue(1000);
        note1000.setAvailableNotes(10);

        CashReport note500 = new CashReport();
        note500.setNoteType("500 Note");
        note500.setValue(500);
        note500.setAvailableNotes(20);

        CashReport note100 = new CashReport();
        note100.setNoteType("100 Note");
        note100.setValue(100);
        note100.setAvailableNotes(50);

        CashReport note50 = new CashReport();
        note50.setNoteType("50 Note");
        note50.setValue(50);
        note50.setAvailableNotes(70);

        CashReport note20 = new CashReport();
        note20.setNoteType("20 Note");
        note20.setValue(20);
        note20.setAvailableNotes(100);

        automaticTellerMachineRepository.saveAll(
                Arrays.asList(note1000, note500, note100, note50, note20)
        );

        automaticTellerMachineRepository.flush();

        LOGGER.info("Finish inserting cash report.");
    }
}
