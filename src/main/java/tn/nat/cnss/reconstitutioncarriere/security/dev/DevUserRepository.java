package tn.nat.cnss.reconstitutioncarriere.security.dev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DevUserRepository extends JpaRepository<Utilisateur, String>{
	
	@Query("SELECT u from tn.nat.cnss.reconstitutioncarriere.security.dev.Utilisateur u WHERE u.matricule = :matricule")
	public Utilisateur getUserByMatricule(@Param("matricule") String matricule);

}
