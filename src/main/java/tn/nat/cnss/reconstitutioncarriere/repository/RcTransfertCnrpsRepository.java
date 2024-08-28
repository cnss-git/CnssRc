package tn.nat.cnss.reconstitutioncarriere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.nat.cnss.reconstitutioncarriere.model.CarriereAssure;
import tn.nat.cnss.reconstitutioncarriere.model.RcTransfertCnrps;

public interface RcTransfertCnrpsRepository extends JpaRepository<RcTransfertCnrps, Long>{

	long countByAssMatAndAssCle(Integer assMat, Short assCle);

	@SuppressWarnings("el-syntax")
	@Query(
			"SELECT COUNT(tc) " +
			"FROM RcTransfertCnrps tc " +
			"WHERE tc.assMat = :#{#carriereAssure.id.assMat} " +
			"AND tc.assCle = :#{#carriereAssure.id.assCle} " +
			"AND tc.trimestre = :#{#carriereAssure.id.trimestre} " +
			"AND tc.annee = :#{#carriereAssure.id.annee} " +
			"AND tc.employeur.id.empMat = :#{#carriereAssure.empMat} " +
			"AND tc.employeur.id.empCle = :#{#carriereAssure.empCle} " +
			"AND tc.typeSalaire.codeTypeSalaire = :#{#carriereAssure.id.typeSalaire} "
	)
	long countByLigneCarriere(@Param("carriereAssure") CarriereAssure carriereAssure);

	@SuppressWarnings("el-syntax")
	@Query(
			"SELECT tc " +
			"FROM RcTransfertCnrps tc " +
			"WHERE tc.assMat = :#{#carriereAssure.id.assMat} " +
			"AND tc.assCle = :#{#carriereAssure.id.assCle} " +
			"AND tc.trimestre = :#{#carriereAssure.id.trimestre} " +
			"AND tc.annee = :#{#carriereAssure.id.annee} " +
			"AND tc.employeur.id.empMat = :#{#carriereAssure.empMat} " +
			"AND tc.employeur.id.empCle = :#{#carriereAssure.empCle} " +
			"AND tc.typeSalaire.codeTypeSalaire = :#{#carriereAssure.id.typeSalaire} "
	)
	RcTransfertCnrps findByLigneCarriere(@Param("carriereAssure") CarriereAssure carriereAssure);

}