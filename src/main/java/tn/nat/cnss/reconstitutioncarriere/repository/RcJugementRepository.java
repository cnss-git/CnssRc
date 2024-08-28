package tn.nat.cnss.reconstitutioncarriere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.nat.cnss.reconstitutioncarriere.model.RcJugement;

public interface RcJugementRepository extends JpaRepository<RcJugement, Long>{

	@Query("SELECT j FROM RcJugement j WHERE j.assMat = :assMat and j.assCle = :assCle")
	List<RcJugement> findByAssure(@Param("assMat") Integer assMat, @Param("assCle") Short assCle);

}
