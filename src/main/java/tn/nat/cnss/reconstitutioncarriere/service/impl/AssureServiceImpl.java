package tn.nat.cnss.reconstitutioncarriere.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tn.nat.cnss.reconstitutioncarriere.model.Assure;
import tn.nat.cnss.reconstitutioncarriere.model.AssureId;
import tn.nat.cnss.reconstitutioncarriere.model.Beneficiaire;
import tn.nat.cnss.reconstitutioncarriere.model.ClasProf;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.repository.AssureRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.BeneficiaireRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.ClasProfRepository;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;

@Service(value="assureService")
public class AssureServiceImpl implements AssureService{
	
	@Value( "${assure_api_base_url}" )
	private String assureApiBaseUrl;
	@Value( "${assure_find_assure_url}" )
	private String findAssureUrl;
	
	@Autowired
	private BeneficiaireRepository beneficiaireRepository;
	
	@Autowired
	private ClasProfRepository clasProfRepository;
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private AssureRepository assureRepository;
	
	

	@Override
	public AssureDto findAssure(Integer assMat, Short assCle){
		/*
		String url = assureApiBaseUrl.concat(findAssureUrl).concat(assMat.toString().concat("/").concat(assCle.toString()));	     
	    RestTemplate restTemplate = new RestTemplate();	    
	    try {
		    ResponseEntity<AssureDto> response = restTemplate.getForEntity(url, AssureDto.class);		    
		    //System.out.println(response.getStatusCodeValue());
		    //System.out.println(response.getBody());		    
		    return response.getBody();
	    }catch(HttpStatusCodeException e) {
	    	return findAssureFromDb(assMat, assCle);
	    	//if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
	    		//return null;
	    	//}else {
	    		//return findAssureFromDb(assMat, assCle);
	    	//}
	    }catch(ResourceAccessException e) {	    	
	    	return findAssureFromDb(assMat, assCle);
	    }
		*/
		return findAssureFromDb(assMat, assCle);
	}
	
	
	
	private AssureDto findAssureFromDb(Integer assMat, Short assCle) {
		Beneficiaire beneficiaire = beneficiaireRepository.findOneByAssMatAndAssCleAndBenTypeAndBenRang(assMat, assCle, (short)1, (short)0); 
		if(beneficiaire == null)
			return null;		
		AssureDto assureDto = new AssureDto();
		assureDto.setAssMat(assMat);
		assureDto.setAssCle(assCle);
		assureDto.setBenPrenomFr(beneficiaire.getBenPrenomFr());
		assureDto.setBenPrenomAr(beneficiaire.getBenPrenomAr());
		assureDto.setBenNomFr(beneficiaire.getBenNomFr());
		assureDto.setBenNomAr(beneficiaire.getBenNomAr());
		assureDto.setBenPrenomPereFr(beneficiaire.getBenPrenomPereFr());
		assureDto.setBenPrenomPereAr(beneficiaire.getBenPrenomPereAr());
		assureDto.setBenPrenomGrandpereFr(beneficiaire.getBenPrenomGrandpereFr());
		assureDto.setBenPrenomGrandpereAr(beneficiaire.getBenPrenomGrandpereAr());
		assureDto.setBenDtnais(beneficiaire.getBenDtnais());
		assureDto.setAssDteff(beneficiaire.getAssure().getAssDteff());
		assureDto.setAssDtassuj(beneficiaire.getAssure().getAssDtassuj());
		assureDto.setIu(beneficiaire.getAssureCress().getIu());
		assureDto.setRegCod(beneficiaire.getAssure().getRegime().getRegCod());
		assureDto.setTypeRegime(beneficiaire.getAssure().getRegime().getTypeRegime());
		if(assureDto.getTypeRegime().compareTo((short) 3) == 0 ) {
			ClasProf classe =  clasProfRepository.findCurrentClasseByTns(assMat, assCle);
			assureDto.setCategorieTns(classe.getId().getClaCod());
		}
		if(beneficiaire.getHistSituations().size() == 0) {
			assureDto.setSituation( (short) 0 );
		}else{
			assureDto.setSituation(beneficiaire.getHistSituations().get(beneficiaire.getHistSituations().size()-1).getId().getSitCod());
		}
		return assureDto;
	}
	
	
	
	
	public Map<String, String> checkCleAssure(Integer assMat, Short assCle){
		
		Map<String, String> errors = new HashMap<String, String>();
		int errorIndex = -1;

		// Vérifier concordance matricule / clé
		Query query = em.createNativeQuery("select referentiel.gen_cleass@prod_prods(:matricule, -1) from dual");
		query.setParameter("matricule", assMat);
		if( ((BigDecimal) query.getSingleResult()).compareTo(BigDecimal.valueOf(assCle)) != 0) {
			errors.put(String.valueOf(errorIndex), "cle_errone");
			errorIndex--;
		}
		
		return errors;
		
	}
	




	@Override
	public Assure findMappedAssure(Integer assMat, Short assCle) {
		return assureRepository.findById(new AssureId(assMat, assCle)).orElse(null);
	}
	
	
	

	public String getAssureApiBaseUrl() {
		return assureApiBaseUrl;
	}
	public void setAssureApiBaseUrl(String assureApiBaseUrl) {
		this.assureApiBaseUrl = assureApiBaseUrl;
	}

	public String getFindAssureUrl() {
		return findAssureUrl;
	}
	public void setFindAssureUrl(String findAssureUrl) {
		this.findAssureUrl = findAssureUrl;
	}
	
	
	
	
	
	
}
