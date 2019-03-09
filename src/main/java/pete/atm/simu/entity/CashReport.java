package pete.atm.simu.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CashReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String noteType;
    private Integer value;
    private Integer availableNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getAvailableNotes() {
        return availableNotes;
    }

    public void setAvailableNotes(Integer availableNotes) {
        this.availableNotes = availableNotes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CashReport){
            CashReport atm = (CashReport) obj;
            return atm.noteType.equals(this.noteType) &&
                    atm.value == this.value &&
                    atm.availableNotes == this.availableNotes;
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
