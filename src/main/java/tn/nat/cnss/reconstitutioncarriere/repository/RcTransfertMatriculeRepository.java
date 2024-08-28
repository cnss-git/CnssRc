package tn.nat.cnss.reconstitutioncarriere.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.nat.cnss.reconstitutioncarriere.model.RcTransfertMatricule;

public interface RcTransfertMatriculeRepository extends JpaRepository<RcTransfertMatricule, Long>{

	RcTransfertMatricule findOneByAssMatSourceAndAssCleSourceAndAssMatDestinationAndAssCleDestination(
		Integer assMatSource,
		Short assCleSource, 
		Integer assMatDestination, 
		Short assCleDestination
	);

}
