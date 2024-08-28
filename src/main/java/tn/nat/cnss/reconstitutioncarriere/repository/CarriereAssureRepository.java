package tn.nat.cnss.reconstitutioncarriere.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.nat.cnss.reconstitutioncarriere.model.CarriereAssure;
import tn.nat.cnss.reconstitutioncarriere.model.CarriereAssureId;

public interface CarriereAssureRepository extends JpaRepository<CarriereAssure, CarriereAssureId>{

	
	@Query(
			"SELECT c " +
			"FROM CarriereAssure c " +
			"WHERE c.id.assMat = :assMat " +
			"AND c.id.assCle = :assCle "+
			"AND ( (c.id.annee > :anneeDepart) or ( (c.id.annee = :anneeDepart) AND (c.id.trimestre >= :trimestreDepart) ) )" +
			"AND ( (c.id.annee < :anneeFin) or ( (c.id.annee = :anneeFin) AND (c.id.trimestre <= :trimestreFin) ) )"
	)	
	List<CarriereAssure> findByAssureMatriculeCleTrimDepartTrimFin(
			@Param("assMat") Integer assMat, 
			@Param("assCle") Short assCle, 
			@Param("trimestreDepart") Short trimestreDepart, 
			@Param("anneeDepart") Short anneeDepart, 
			@Param("trimestreFin") Short trimestreFin, 
			@Param("anneeFin") Short anneeFin
	);

	
	List<CarriereAssure> findByIdAssMatAndIdAssCle(Integer assMat, Short assCle);
	

}
