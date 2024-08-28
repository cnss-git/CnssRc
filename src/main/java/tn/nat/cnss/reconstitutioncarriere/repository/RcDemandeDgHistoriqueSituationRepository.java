package tn.nat.cnss.reconstitutioncarriere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDg;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgHistoriquSituation;

public interface RcDemandeDgHistoriqueSituationRepository extends JpaRepository<RcDemandeDgHistoriquSituation, Long>{

	List<RcDemandeDgHistoriquSituation> findByDemandeDg(RcDemandeDg rcDemandeDg);

}
