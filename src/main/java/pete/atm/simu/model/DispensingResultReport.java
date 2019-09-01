package pete.atm.simu.model;

import lombok.Data;
import pete.atm.simu.entity.CashReport;
import java.util.List;
import java.util.Objects;

@Data
public class DispensingResultReport {
    private final List<CashReport> cashReports;
    private String error;

    public DispensingResultReport(List<CashReport> cashReports){
        this.cashReports = cashReports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DispensingResultReport that = (DispensingResultReport) o;
        return Objects.equals(cashReports, that.cashReports) &&
                Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cashReports, error);
    }

    @Override
    public String toString() {
        return "DispensingResultReport{" +
                "cashReports=" + cashReports +
                '}';
    }
}
