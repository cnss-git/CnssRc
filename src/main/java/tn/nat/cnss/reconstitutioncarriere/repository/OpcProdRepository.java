package tn.nat.cnss.reconstitutioncarriere.repository;

import tn.nat.cnss.reconstitutioncarriere.model.dto.RachatDto;
import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;

public interface OpcProdRepository {

	void createRachatOrdrePaiement(RachatDto rachat, Structure structure, String matriculeAgent);

}
