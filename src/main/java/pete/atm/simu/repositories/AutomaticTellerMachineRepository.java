package pete.atm.simu.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pete.atm.simu.entity.CashReport;

public interface AutomaticTellerMachineRepository extends JpaRepository<CashReport, Long> {
}
