package pete.atm.simu;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pete.atm.simu.entity.CashReport;
import pete.atm.simu.exception.ATMException;
import pete.atm.simu.exception.DispensingAmountOutOfRangeException;
import pete.atm.simu.exception.UnsupportedDispensingAmountException;
import pete.atm.simu.model.DispensingResultReport;
import pete.atm.simu.repositories.AutomaticTellerMachineRepository;
import pete.atm.simu.services.AutomaticTellerMachineService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AtmApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class DispensingCashServiceITTests extends TestCase {

    @Autowired
    private AutomaticTellerMachineRepository automaticTellerMachineRepository;

    @Autowired
    private AutomaticTellerMachineService automaticTellerMachineService;

    @Test
    public void testAllNotesAreUsed() {

        // Populate test data
        List<CashReport> availableCashReports = new ArrayList<>();
        availableCashReports.add(new CashReport(1L, "1000 Note", 1000, 2));
        availableCashReports.add(new CashReport(2L, "500 Note", 500, 10));
        availableCashReports.add(new CashReport(3L, "100 Note", 100, 9));
        availableCashReports.add(new CashReport(4L, "50 Note", 50, 2));
        availableCashReports.add(new CashReport(5L, "20 Note", 20, 5));

        automaticTellerMachineRepository.saveAll(availableCashReports);
        automaticTellerMachineRepository.flush();

        try {
            DispensingResultReport actualResult = automaticTellerMachineService.dispensingCash(1670);
            List<CashReport> dispensedCashReports= actualResult.getDispensedCashReports();

            // All note types must be used exactly one.
            dispensedCashReports.forEach(dispensedCashReport -> {
                assertEquals(1, dispensedCashReport.getAvailableNotes().intValue());
            });

            // Assert that each note in ATM has been decreased due to dispensing.
            availableCashReports = automaticTellerMachineRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));
            // note 1000
            assertEquals(1, availableCashReports.get(0).getAvailableNotes().intValue());
            // note 500
            assertEquals(9, availableCashReports.get(1).getAvailableNotes().intValue());
            // note 100
            assertEquals(8, availableCashReports.get(2).getAvailableNotes().intValue());
            // note 50
            assertEquals(1, availableCashReports.get(3).getAvailableNotes().intValue());
            // note 20
            assertEquals(4, availableCashReports.get(4).getAvailableNotes().intValue());

        }catch (Exception ex){
            fail("Exception must not be thrown.");
        }
    }

    @Test
    public void testLowerNoteTypeCoversBiggerNotes() {
        // Populate test data
        List<CashReport> availableCashReports = new ArrayList<>();

        // No note 1000 and 100 this time
        availableCashReports.add(new CashReport(1L, "1000 Note", 1000, 0));
        availableCashReports.add(new CashReport(2L, "500 Note", 500, 10));
        availableCashReports.add(new CashReport(3L, "100 Note", 100, 0));
        availableCashReports.add(new CashReport(4L, "50 Note", 50, 3));
        availableCashReports.add(new CashReport(5L, "20 Note", 20, 5));

        automaticTellerMachineRepository.saveAll(availableCashReports);
        automaticTellerMachineRepository.flush();

        try {
            DispensingResultReport actualResult = automaticTellerMachineService.dispensingCash(1690);
            List<CashReport> dispensedCashReports= actualResult.getDispensedCashReports();

            // The lower note type must cover the bigger note if ATM does not have the bigger notes.
            for (CashReport dispensedCashReport : dispensedCashReports){
                switch (dispensedCashReport.getValue()){
                    case 1000:
                        assertEquals(0 ,dispensedCashReport.getAvailableNotes().intValue());
                        break;
                    case 500:
                        assertEquals(3 ,dispensedCashReport.getAvailableNotes().intValue());
                        break;
                    case 100:
                        assertEquals(0 ,dispensedCashReport.getAvailableNotes().intValue());
                        break;
                    case 50:
                        assertEquals(3 ,dispensedCashReport.getAvailableNotes().intValue());
                        break;
                    case 20:
                        assertEquals(2 ,dispensedCashReport.getAvailableNotes().intValue());
                        break;
                }

            }

            // Assert that each note in ATM has been decreased due to dispensing.
            availableCashReports = automaticTellerMachineRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));
            // note 1000
            assertEquals(0, availableCashReports.get(0).getAvailableNotes().intValue());
            // note 500
            assertEquals(7, availableCashReports.get(1).getAvailableNotes().intValue());
            // note 100
            assertEquals(0, availableCashReports.get(2).getAvailableNotes().intValue());
            // note 50
            assertEquals(0, availableCashReports.get(3).getAvailableNotes().intValue());
            // note 20
            assertEquals(3, availableCashReports.get(4).getAvailableNotes().intValue());

        }catch (Exception ex){
            fail("Exception must not be thrown.");
        }
    }

    @Test
    public void testAmountEndsWithEighty() {
        // Populate test data
        List<CashReport> availableCashReports = new ArrayList<>();

        availableCashReports.add(new CashReport(1L, "1000 Note", 1000, 0));
        availableCashReports.add(new CashReport(2L, "500 Note", 500, 0));
        availableCashReports.add(new CashReport(3L, "100 Note", 100, 0));
        availableCashReports.add(new CashReport(4L, "50 Note", 50, 3));
        availableCashReports.add(new CashReport(5L, "20 Note", 20, 5));

        automaticTellerMachineRepository.saveAll(availableCashReports);
        automaticTellerMachineRepository.flush();

        try {
            DispensingResultReport actualResult = automaticTellerMachineService.dispensingCash(80);
            List<CashReport> dispensedCashReports= actualResult.getDispensedCashReports();

            // The lower note type must cover the bigger note if ATM does not have the bigger notes.
            for (CashReport dispensedCashReport : dispensedCashReports){
                switch (dispensedCashReport.getValue()){
                    case 50:
                        assertEquals(0 ,dispensedCashReport.getAvailableNotes().intValue());
                        break;
                    case 20:
                        assertEquals(4 ,dispensedCashReport.getAvailableNotes().intValue());
                        break;
                }

            }

            // Note type 50 must not be used in this case.
            availableCashReports = automaticTellerMachineRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));
            // note 50
            assertEquals(3, availableCashReports.get(3).getAvailableNotes().intValue());
            // note 20
            assertEquals(1, availableCashReports.get(4).getAvailableNotes().intValue());

        }catch (Exception ex){
            fail("Exception must not be thrown.");
        }
    }

    @Test
    public void testAmountEndsWithTen() {
        // Populate test data
        List<CashReport> availableCashReports = new ArrayList<>();

        availableCashReports.add(new CashReport(1L, "1000 Note", 1000, 1));
        availableCashReports.add(new CashReport(2L, "500 Note", 500, 1));
        availableCashReports.add(new CashReport(3L, "100 Note", 100, 1));
        availableCashReports.add(new CashReport(4L, "50 Note", 50, 3));
        availableCashReports.add(new CashReport(5L, "20 Note", 20, 5));


        automaticTellerMachineRepository.saveAll(availableCashReports);
        automaticTellerMachineRepository.flush();

        try {
            automaticTellerMachineService.dispensingCash(1610);
            fail("UnsupportedDispensingAmountException must not be thrown.");
        }catch (ATMException ex){
            assertTrue(ex instanceof UnsupportedDispensingAmountException);
            assertEquals("Current notes that ATM has do not support this dispensing amount.", ex.getMessage());

            availableCashReports = automaticTellerMachineRepository.findAll(Sort.by(Sort.Direction.DESC, "value"));

            // No note is decreased.
            // note 1000
            assertEquals(1, availableCashReports.get(0).getAvailableNotes().intValue());
            // note 500
            assertEquals(1, availableCashReports.get(1).getAvailableNotes().intValue());
            // note 100
            assertEquals(1, availableCashReports.get(2).getAvailableNotes().intValue());
            // note 50
            assertEquals(3, availableCashReports.get(3).getAvailableNotes().intValue());
            // note 20
            assertEquals(5, availableCashReports.get(4).getAvailableNotes().intValue());

        }
    }

    @Test
    public void testDispensingOutOfRangeAmount(){
        try{
           automaticTellerMachineService.dispensingCash(-1);
           fail("DispensingAmountOutOfRangeException must be thrown.");
        }catch (ATMException ex){
            assertTrue(ex instanceof DispensingAmountOutOfRangeException);
            assertEquals("Dispensing amount must be in between 20 and 30000.", ex.getMessage());
        }

        try{
            automaticTellerMachineService.dispensingCash(30001);
            fail("DispensingAmountOutOfRangeException must be thrown.");
        }catch (ATMException ex){
            assertTrue(ex instanceof DispensingAmountOutOfRangeException);
            assertEquals("Dispensing amount must be in between 20 and 30000.", ex.getMessage());
        }
    }

}
