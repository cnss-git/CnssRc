package tn.nat.cnss.reconstitutioncarriere.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.nat.cnss.reconstitutioncarriere.model.TauxRachat;

public interface TauxRachatRepository extends JpaRepository<TauxRachat, Short>{

	@Query("SELECT t FROM TauxRachat t WHERE t.ageMin <= :age AND t.ageMax >= :age")
	TauxRachat findTauxByAge(@Param("age") Short age);

}
