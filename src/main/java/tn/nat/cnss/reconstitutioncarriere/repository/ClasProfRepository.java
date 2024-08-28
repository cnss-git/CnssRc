package tn.nat.cnss.reconstitutioncarriere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.nat.cnss.reconstitutioncarriere.model.ClasProdId;
import tn.nat.cnss.reconstitutioncarriere.model.ClasProf;

public interface ClasProfRepository extends JpaRepository<ClasProf, ClasProdId>{

	@Query(
			"SELECT cp FROM ClasProf cp " +
			"WHERE cp.id.assMat = :assMat " +
			"AND cp.id.assCle = :assCle " +
			"AND cp.id.dateDepart = " +
			"(" +
			" SELECT MAX(cp2.id.dateDepart) FROM ClasProf cp2 WHERE cp2.id.assMat = :assMat AND cp2.id.assCle = :assCle " +
			")"
	)
	ClasProf findCurrentClasseByTns(@Param("assMat") Integer assMat, @Param("assCle") Short assCle);

}
