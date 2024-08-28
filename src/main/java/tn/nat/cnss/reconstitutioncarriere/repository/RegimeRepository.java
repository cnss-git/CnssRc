package tn.nat.cnss.reconstitutioncarriere.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.nat.cnss.reconstitutioncarriere.model.Regime;

public interface RegimeRepository extends JpaRepository<Regime, Short>{
	
	Page<Regime> findAll(Pageable page);

}
