package tn.nat.cnss.reconstitutioncarriere.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.nat.cnss.reconstitutioncarriere.model.Beneficiaire;

public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, Long>{

	Beneficiaire findOneByAssMatAndAssCleAndBenTypeAndBenRang(Integer assMat, Short assCle, Short benType, Short benRang);

}
