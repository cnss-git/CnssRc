package tn.nat.cnss.reconstitutioncarriere.service;

import java.util.Map;

import tn.nat.cnss.reconstitutioncarriere.model.Assure;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;

public interface AssureService {

	AssureDto findAssure(Integer assMat, Short assCle);

	Map<String, String> checkCleAssure(Integer assMat, Short assCle);

	Assure findMappedAssure(Integer assMat, Short assCle);

}
