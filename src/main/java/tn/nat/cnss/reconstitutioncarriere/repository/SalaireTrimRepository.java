package tn.nat.cnss.reconstitutioncarriere.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.nat.cnss.reconstitutioncarriere.model.SalaireTrim;
import tn.nat.cnss.reconstitutioncarriere.model.SalaireTrimId;

public interface SalaireTrimRepository extends JpaRepository<SalaireTrim, SalaireTrimId>{

	List<SalaireTrim> findByIdAssMatAndIdAssCleOrderByIdAnneeAscIdTrimestreAsc(Integer assMat, Short assCle);
	
}
