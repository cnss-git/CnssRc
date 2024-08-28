package tn.nat.cnss.reconstitutioncarriere.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.nat.cnss.reconstitutioncarriere.model.Employeur;
import tn.nat.cnss.reconstitutioncarriere.model.EmployeurId;

public interface EmployeurRepository extends JpaRepository<Employeur, EmployeurId>{

	@Query("SELECT e FROM Employeur e WHERE ( (e.empRais LIKE CONCAT('%',:pEmpRais,'%')) OR (:pEmpRais IS NULL) )  AND  ( (e.id.empMat > :pMatricule) OR (:pMatricule IS NULL) )")
	Page<Employeur> find(@Param("pEmpRais") String pEmpRais,@Param("pMatricule") Integer pMatricule, Pageable pageable);

	//Page<Employeur> findAll(Pageable page);
	
	@Query("SELECT COUNT(e) FROM Employeur e WHERE ( (e.empRais LIKE CONCAT('%',:pEmpRais,'%')) OR (:pEmpRais IS NULL) )  AND  ( (e.id.empMat > :pMatricule) OR (:pMatricule IS NULL) )")
	long count(@Param("pEmpRais") String pEmpRais,@Param("pMatricule") Integer pMatricule);

}
