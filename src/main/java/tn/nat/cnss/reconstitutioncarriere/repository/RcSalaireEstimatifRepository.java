package tn.nat.cnss.reconstitutioncarriere.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.nat.cnss.reconstitutioncarriere.model.RcSalaireEstimatif;

public interface RcSalaireEstimatifRepository extends JpaRepository<RcSalaireEstimatif, Long>{

	@Query("SELECT se FROM RcSalaireEstimatif se WHERE se.assMat = :assMat and se.assCle = :assCle")
	Optional<RcSalaireEstimatif> findByAssure(@Param("assMat") Integer assMat, @Param("assCle") Short assCle);

}
