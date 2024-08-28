package tn.nat.cnss.reconstitutioncarriere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.nat.cnss.reconstitutioncarriere.model.Assure;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDg;
import tn.nat.cnss.reconstitutioncarriere.model.dto.RcDemandeDgDto;
import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;

public interface RcDemandeDgRepository extends JpaRepository<RcDemandeDg, Long>{

	List<RcDemandeDg> findByBureau(Structure bureau);

	List<RcDemandeDg> findByAssure(Assure assure);

	@Query(	
			"SELECT new tn.nat.cnss.reconstitutioncarriere.model.dto.RcDemandeDgDto"+
			"(d.id, d.assure.id.assMat, d.assure.id.assCle, d.objetDemande.code, d.objetDemande.libelleAr, hs.situation.code, hs.situation.libelleAr, hs.dateSituation) "+
			"FROM RcDemandeDg d, RcDemandeDgHistoriquSituation hs "+
			//"LEFT JOIN EtatLiquidation e ON e.pk.bureau = m.burCod  AND e.pk.matricule = m.cotIde  AND e.pk.cle = m.cotCle AND e.pk.trimestre = m.etlTrim AND e.pk.annee = m.etlAnn  AND e.pk.ordre = m.etlNumord "+
			//"LEFT JOIN NatureEtl n ON n.codeNature = e.natureEtl.codeNature "+
			"WHERE d.bureau.burCod = :codeStructure "+
			"AND hs.demandeDg.id = d.id "+
			"AND hs.id = (SELECT MAX(hs2.id) FROM RcDemandeDgHistoriquSituation hs2 WHERE hs2.demandeDg.id = d.id) "
	)	
	List<RcDemandeDgDto> findAllDemandesByBureau(@Param("codeStructure") Short codeStructure);

}
