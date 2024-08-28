package tn.nat.cnss.reconstitutioncarriere.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.nat.cnss.reconstitutioncarriere.model.Assure;
import tn.nat.cnss.reconstitutioncarriere.model.AssureId;

public interface AssureRepository extends JpaRepository<Assure, AssureId>{

}
