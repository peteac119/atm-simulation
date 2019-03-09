package pete.atm.simu;

import com.google.gson.Gson;
import junit.framework.TestCase;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pete.atm.simu.entity.CashReport;
import pete.atm.simu.model.DispensingResultReport;
import pete.atm.simu.repositories.AutomaticTellerMachineRepository;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AtmApplication.class
)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ATMControllerITTests extends TestCase {

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

    @LocalServerPort
    private int port;

    @Autowired
    private AutomaticTellerMachineRepository automaticTellerMachineRepository;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testDispensingAmountOutOfRangeErrorResponse() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/atm/10"),
                HttpMethod.GET, this.entity, String.class);

        Gson g = new Gson();
        DispensingResultReport dispensingResultReport = g.fromJson(response.getBody(), DispensingResultReport.class);

        assertNull(dispensingResultReport.getAvailableCashReports());
        assertNull(dispensingResultReport.getDispensedCashReports());
        assertEquals("Dispensing amount must be in between 20 and 30000.", dispensingResultReport.getError());
    }

    @Test
    public void testUnsupportedDispensingAmountExceptionErrorResponse() {

        // Populate test data
        List<CashReport> cashReports = new ArrayList<>();

        // No note 1000 and 100 this time
        cashReports.add(new CashReport(1L, "1000 Note", 1000, 0));
        cashReports.add(new CashReport(2L, "500 Note", 500, 0));
        cashReports.add(new CashReport(3L, "100 Note", 100, 0));
        cashReports.add(new CashReport(4L, "50 Note", 50, 0));
        cashReports.add(new CashReport(5L, "20 Note", 20, 1));

        automaticTellerMachineRepository.saveAll(cashReports);
        automaticTellerMachineRepository.flush();

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/atm/40"),
                HttpMethod.GET, this.entity, String.class);

        Gson g = new Gson();
        DispensingResultReport dispensingResultReport = g.fromJson(response.getBody(), DispensingResultReport.class);

        assertNull(dispensingResultReport.getAvailableCashReports());
        assertNull(dispensingResultReport.getDispensedCashReports());
        assertEquals("Current notes that ATM has do not support this dispensing amount.", dispensingResultReport.getError());
    }

    @Test
    public void testSuccessResponse(){

        // Populate test data
        List<CashReport> cashReports = new ArrayList<>();

        // No note 1000 and 100 this time
        cashReports.add(new CashReport(1L, "1000 Note", 1000, 0));
        cashReports.add(new CashReport(2L, "500 Note", 500, 10));
        cashReports.add(new CashReport(3L, "100 Note", 100, 0));
        cashReports.add(new CashReport(4L, "50 Note", 50, 3));
        cashReports.add(new CashReport(5L, "20 Note", 20, 5));

        automaticTellerMachineRepository.saveAll(cashReports);
        automaticTellerMachineRepository.flush();

        // Call API to assert the result from controller.
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/atm/1690"),
                HttpMethod.GET, this.entity, String.class);

        Gson g = new Gson();
        DispensingResultReport dispensingResultReport = g.fromJson(response.getBody(), DispensingResultReport.class);

        System.out.println(dispensingResultReport);

        assertNull(dispensingResultReport.getError());
        assertNotNull(dispensingResultReport.getAvailableCashReports());
        assertNotNull(dispensingResultReport.getDispensedCashReports());

        List<CashReport> availableCashReports = dispensingResultReport.getAvailableCashReports();
        List<CashReport> dispensingResultReports = dispensingResultReport.getDispensedCashReports();

        // Assert available cash report response
        // Note 1000
        assertEquals(1L, availableCashReports.get(0).getId().longValue());
        assertEquals("1000 Note", availableCashReports.get(0).getNoteType());
        assertEquals(1000, availableCashReports.get(0).getValue().intValue());
        assertEquals(0, availableCashReports.get(0).getAvailableNotes().intValue());

        // Note 500
        assertEquals(2L, availableCashReports.get(1).getId().longValue());
        assertEquals("500 Note", availableCashReports.get(1).getNoteType());
        assertEquals(500, availableCashReports.get(1).getValue().intValue());
        assertEquals(7, availableCashReports.get(1).getAvailableNotes().intValue());

        // Note 100
        assertEquals(3L, availableCashReports.get(2).getId().longValue());
        assertEquals("100 Note", availableCashReports.get(2).getNoteType());
        assertEquals(100, availableCashReports.get(2).getValue().intValue());
        assertEquals(0, availableCashReports.get(2).getAvailableNotes().intValue());

        // Note 50
        assertEquals(4L, availableCashReports.get(3).getId().longValue());
        assertEquals("50 Note", availableCashReports.get(3).getNoteType());
        assertEquals(50, availableCashReports.get(3).getValue().intValue());
        assertEquals(0, availableCashReports.get(3).getAvailableNotes().intValue());

        // Note 20
        assertEquals(5L, availableCashReports.get(4).getId().longValue());
        assertEquals("20 Note", availableCashReports.get(4).getNoteType());
        assertEquals(20, availableCashReports.get(4).getValue().intValue());
        assertEquals(3, availableCashReports.get(4).getAvailableNotes().intValue());


        // Assert actual cash report result of user
        // Note 1000
        assertNull(dispensingResultReports.get(0).getId());
        assertEquals("1000 Note", dispensingResultReports.get(4).getNoteType());
        assertEquals(1000, dispensingResultReports.get(4).getValue().intValue());
        assertEquals(0, dispensingResultReports.get(4).getAvailableNotes().intValue());

        // Note 500
        assertNull(dispensingResultReports.get(1).getId());
        assertEquals("500 Note", dispensingResultReports.get(3).getNoteType());
        assertEquals(500, dispensingResultReports.get(3).getValue().intValue());
        assertEquals(3, dispensingResultReports.get(3).getAvailableNotes().intValue());

        // Note 100
        assertNull(dispensingResultReports.get(2).getId());
        assertEquals("100 Note", dispensingResultReports.get(2).getNoteType());
        assertEquals(100, dispensingResultReports.get(2).getValue().intValue());
        assertEquals(0, dispensingResultReports.get(2).getAvailableNotes().intValue());

        // Note 50
        assertNull(dispensingResultReports.get(3).getId());
        assertEquals("50 Note", dispensingResultReports.get(1).getNoteType());
        assertEquals(50, dispensingResultReports.get(1).getValue().intValue());
        assertEquals(3, dispensingResultReports.get(1).getAvailableNotes().intValue());

        // Note 20
        assertNull(dispensingResultReports.get(4).getId());
        assertEquals("20 Note", dispensingResultReports.get(0).getNoteType());
        assertEquals(20, dispensingResultReports.get(0).getValue().intValue());
        assertEquals(2, dispensingResultReports.get(0).getAvailableNotes().intValue());
    }
}
