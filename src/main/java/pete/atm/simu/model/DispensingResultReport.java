package pete.atm.simu.model;

import pete.atm.simu.entity.CashReport;
import java.util.List;

public class DispensingResultReport {
    private final List<CashReport> cashReports;
    private String error;

    public DispensingResultReport(List<CashReport> cashReports){
        this.cashReports = cashReports;
    }

    public List<CashReport> getCashReports() {
        return cashReports;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DispensingResultReport{" +
                "cashReports=" + cashReports +
                '}';
    }
}
