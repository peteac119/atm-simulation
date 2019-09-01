package pete.atm.simu.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "cash_report")
public class CashReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String noteType;
    private Integer value;
    private Integer availableNotes;

    public CashReport() {}

    public CashReport(Long id, String noteType, Integer value, Integer availableNotes) {
        this.id = id;
        this.noteType = noteType;
        this.value = value;
        this.availableNotes = availableNotes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, noteType, value, availableNotes);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CashReport){
            CashReport atm = (CashReport) obj;
            return atm.noteType.equals(this.noteType) &&
                    atm.value.equals(this.value) &&
                    atm.availableNotes.equals(this.availableNotes);
        }

        return false;
    }

    @Override
    public String toString() {
        return "CashReport{" +
                "id=" + id +
                ", noteName='" + noteType + '\'' +
                ", value=" + value +
                ", availableNotes=" + availableNotes +
                '}';
    }
}
