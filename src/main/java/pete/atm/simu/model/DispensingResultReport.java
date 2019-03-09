package pete.atm.simu.model;

import pete.atm.simu.entity.CashReport;
import java.util.List;

public class DispensingResultReport {
    private final List<CashReport> availableCashReports;
    private final List<CashReport> dispensedCashReports;
    private String error;

    public DispensingResultReport(List<CashReport> availableCashReports, List<CashReport> dispensedCashReports){
        this.availableCashReports = availableCashReports;
        this.dispensedCashReports = dispensedCashReports;
    }

    public List<CashReport> getAvailableCashReports() {
        return availableCashReports;
    }

    public List<CashReport> getDispensedCashReports() {
        return dispensedCashReports;
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
                "availableCashReports=" + availableCashReports +
                ", dispensedCashReports=" + dispensedCashReports +
                '}';
    }
}
