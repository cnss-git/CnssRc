package tn.nat.cnss.reconstitutioncarriere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDg;
import tn.nat.cnss.reconstitutioncarriere.model.RcDg;
//import tn.nat.cnss.reconstitutioncarriere.model.dto.LigneCarriereDto;

public interface RcDgRepository extends JpaRepository<RcDg, Long>{

	List<RcDg> findByDemandeDg(RcDemandeDg demandeDg);

	/*
	@SuppressWarnings("el-syntax")
	@Query(
			"SELECT dg FROM RcDg dg "+
			"WHERE dg.assMat = :assMat "+
			"AND dg.assCle=:assCle "+
			"AND dg.employeur.id.empMat = :#{#employeur.id.empMat} "+
			"AND dg.rcDgDelete = null "+
			"AND dg.rcDgUpdate = null "+
			"AND dg.rcdgId NOT IN (SELECT NVL(dgDelete.rcDgDelete.rcdgId,0) FROM RcDg dgDelete)"
	)
	List<RcDg> findNonExploiteesByAssureAndEmployeur(@Param("assMat") Integer assMat, @Param("assCle") Short assCle, @Param("employeur") Employeur employeur);
	*/

	/*
	@SuppressWarnings("el-syntax")
	@Query(
			"SELECT dg FROM RcDg dg "+
			"WHERE dg.assMat = :#{#ligneCarriere.assMat} "+
			"AND dg.assCle = :#{#ligneCarriere.assCle} "+
			"AND dg.employeur.id.empMat = :#{#ligneCarriere.empMat} "+
			"AND dg.employeur.id.empCle = :#{#ligneCarriere.empCle} "+
			"AND dg.trimestre = :#{#ligneCarriere.trimestre} "+
			"AND dg.annee = :#{#ligneCarriere.annee} "+
			"AND dg.typeSalaire.codeTypeSalaire = :#{#ligneCarriere.codeTypeSalaire} "+
			"AND dg.rcdgId = 	(SELECT MAX(dg2.rcdgId) FROM RcDg dg2 "+
			"					WHERE dg2.assMat = :#{#ligneCarriere.assMat} "+
			"					AND dg2.assCle = :#{#ligneCarriere.assCle} " + 
			"					AND dg2.employeur.id.empMat = :#{#ligneCarriere.empMat} " + 
			"					AND dg2.employeur.id.empCle = :#{#ligneCarriere.empCle} " + 
			"					AND dg2.trimestre = :#{#ligneCarriere.trimestre} " + 
			"					AND dg2.annee = :#{#ligneCarriere.annee} " + 
			"					AND dg2.typeSalaire.codeTypeSalaire = :#{#ligneCarriere.codeTypeSalaire}) "
	)
	RcDg findLastLigneDgNonexploiteeParLigneCarriere(@Param("ligneCarriere") LigneCarriereDto ligneCarriere);


	@SuppressWarnings("el-syntax")
	@Modifying
	@Query(
			"DELETE FROM RcDg dg " +
			"WHERE dg.assMat=:#{#ligneCarriere.assMat} "+
			"AND dg.assCle=:#{#ligneCarriere.assCle} "+
			"AND dg.employeur.id.empMat=:#{#ligneCarriere.empMat} "+
			"AND dg.employeur.id.empCle=:#{#ligneCarriere.empCle} "+
			"AND dg.trimestre=:#{#ligneCarriere.trimestre} "+
			"AND dg.annee=:#{#ligneCarriere.annee} "+
			"AND dg.typeSalaire.codeTypeSalaire=:#{#ligneCarriere.codeTypeSalaire} "+
			"AND dg.dateExploitation = null "
	)
	@Transactional
	void deleteDgsNonExploitesByDg(@Param("ligneCarriere") LigneCarriereDto toDelete);
	*/
	
	
}
