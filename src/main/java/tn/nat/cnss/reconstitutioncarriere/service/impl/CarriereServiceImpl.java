package tn.nat.cnss.reconstitutioncarriere.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import tn.nat.cnss.reconstitutioncarriere.model.CarriereAssure;
import tn.nat.cnss.reconstitutioncarriere.model.Employeur;
import tn.nat.cnss.reconstitutioncarriere.model.EmployeurId;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDg;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgHistoriquSituation;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgObjet;
import tn.nat.cnss.reconstitutioncarriere.model.RcDg;
import tn.nat.cnss.reconstitutioncarriere.model.RcJugement;
import tn.nat.cnss.reconstitutioncarriere.model.RcSalaireEstimatif;
import tn.nat.cnss.reconstitutioncarriere.model.RcTransfertCnrps;
import tn.nat.cnss.reconstitutioncarriere.model.RcTransfertMatricule;
import tn.nat.cnss.reconstitutioncarriere.model.TauxRachat;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.model.dto.RachatDto;
import tn.nat.cnss.reconstitutioncarriere.model.dto.RcDemandeDgDto;
import tn.nat.cnss.reconstitutioncarriere.repository.CarriereAssureRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.EmployeurRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.OpcProdRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcDemandeDgHistoriqueSituationRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcDemandeDgObjetRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcDemandeDgRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcDemandeDgSituationRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcDgRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcJugementRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcSalaireEstimatifRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcTransfertCnrpsRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.RcTransfertMatriculeRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.TauxRachatRepository;
import tn.nat.cnss.reconstitutioncarriere.repository.TypeSalaireRepository;
import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;
import tn.nat.cnss.reconstitutioncarriere.service.CarriereService;
import tn.nat.cnss.reconstitutioncarriere.util.ApplicationUtil;


@Service("carriereService")
public class CarriereServiceImpl implements CarriereService{
	
	
	
	//--------------------------------------------------------------------------------------------------------------
	@Value( "${carriere_api_base_url}" )
	private String carriereApiBaseUrl;
	
	@Autowired
	private EmployeurRepository employeurRepository;
	
	@Autowired
	private TypeSalaireRepository typeSalaireRepository;
	
	@Autowired
	private RcSalaireEstimatifRepository salaireEstimatifRepository;
	
	@Autowired
	private CarriereAssureRepository carriereAssureRepository; 
	
	@Autowired
	private RcTransfertCnrpsRepository transfertCnrpsRepository;
	
	@Autowired
	private RcTransfertMatriculeRepository transfertMatriculeRepository;
	
	@Autowired
	private TauxRachatRepository tauxRachatRepository;
	
	@Autowired
	private RcJugementRepository jugementRepository; 
	
	@Autowired
	private AssureService assureService;
	
	@Autowired
	private RcDemandeDgRepository rcDemandeDgRepository;
	
	@Autowired
	private RcDemandeDgObjetRepository rcDemandeDgObjetRepository;
	
	@Autowired
	private RcDemandeDgSituationRepository rcDemandeDgSituationRepository;
	
	@Autowired
	private RcDemandeDgHistoriqueSituationRepository rcDemandeDgHistoriqueSituationRepository;
	
	@Autowired
	private RcDgRepository rcDgRepository;
	
	@Autowired
	private EntityManager em;
	
	@Autowired 
	private OpcProdRepository opcProdRepository;
	//--------------------------------------------------------------------------------------------------------------
	
	

	

	//--------------------------------------------------------------------------------------------------------------
	@Override
	public Map<String, String> processSalaireEstimatif(RcSalaireEstimatif rcSalaireEstimatif) {	
		
		Map<String, String> errors = new HashMap<String, String>();
		
		AssureDto assure = assureService.findAssure(rcSalaireEstimatif.getAssMat(), rcSalaireEstimatif.getAssCle());
			
		int errorIndex = -1;
		if (rcSalaireEstimatif.getEmployeur() == null) {
			errors.put(String.valueOf(errorIndex), "empoyeur_inexistant");
			errorIndex--;
		}else if(rcSalaireEstimatif.getEmployeur().getRegime().compareTo(assure.getRegCod()) != 0){
			errors.put(String.valueOf(errorIndex), "regimes_employeur_assure_non_conformes");
			errorIndex--;
		}	
		if (errors.isEmpty()) {
			salaireEstimatifRepository.saveAndFlush(rcSalaireEstimatif);
		}		
		return errors;
	}
	//-----
	
	
	
	//-----
	@Override
	public RcSalaireEstimatif findSalaireEstimatifByAssure(Integer assMat, Short assCle) {
		return salaireEstimatifRepository.findByAssure(assMat, assCle).orElse(null);
	}
	//-----
	
	
	
	//-----
	@Override
	public List<CarriereAssure> loadCarriereByAssureMatriculeCleTrimDepartTrimFin(Integer assMat, Short assCle, Short trimestreDepart, Short anneeDepart, Short trimestreFin, Short anneeFin) {
		
		List<CarriereAssure> listCarriereAssuresForTransfertCnrps = carriereAssureRepository.findByAssureMatriculeCleTrimDepartTrimFin(assMat, assCle, trimestreDepart, anneeDepart, trimestreFin, anneeFin);
		
		if (transfertCnrpsRepository.countByAssMatAndAssCle(assMat, assCle) > 0 ) {
			for(CarriereAssure c : listCarriereAssuresForTransfertCnrps) {
				if(transfertCnrpsRepository.countByLigneCarriere(c) > 0) {
					c.setTransfertCnrps(true);
					c.setDateExploitation(transfertCnrpsRepository.findByLigneCarriere(c).getDateExploitation());
				}else {
					c.setTransfertCnrps(false);
				}
			}
		}else{
			for(CarriereAssure c : listCarriereAssuresForTransfertCnrps) {
				c.setTransfertCnrps(true);
			}
		}
		
		return listCarriereAssuresForTransfertCnrps;
	}
	//-----
	
	
	
	//-----
	@Override
	public void processTransfertCnrps(List<CarriereAssure> listSalairesPourTransfert, String matriculeAgent, Structure structure) {
		for(CarriereAssure c : listSalairesPourTransfert) {
			
			RcTransfertCnrps trimestreToTransfert = transfertCnrpsRepository.findByLigneCarriere(c);
			
			if(c.getTransfertCnrps()) {
				if (trimestreToTransfert == null) {
					trimestreToTransfert = new RcTransfertCnrps();
				}
				trimestreToTransfert.setAssMat(c.getId().getAssMat());
				trimestreToTransfert.setAssCle(c.getId().getAssCle());
				trimestreToTransfert.setEmployeur(employeurRepository.findById(new EmployeurId(c.getEmpMat(), c.getEmpCle())).orElse(null));
				trimestreToTransfert.setTrimestre(c.getId().getTrimestre());
				trimestreToTransfert.setAnnee(c.getId().getAnnee());
				trimestreToTransfert.setTypeSalaire(typeSalaireRepository.findById(c.getId().getTypeSalaire()).orElse(null)/*typeSalaireRepository.findById((short)9).orElse(null)*/);
				trimestreToTransfert.setSalaire(c.getSalaire());
				trimestreToTransfert.setMatriculeAgent(Integer.parseInt(matriculeAgent));
				trimestreToTransfert.setBrSaisie(structure.getBurCod());
				trimestreToTransfert.setCodeValidation(c.getCodeValidation()==null ? " " : c.getCodeValidation());
				transfertCnrpsRepository.saveAndFlush(trimestreToTransfert);
			}else{
				if (trimestreToTransfert != null) {
					transfertCnrpsRepository.delete(trimestreToTransfert);
				}
			}
		}
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> checkTransfertMatricule(AssureDto assureSource, AssureDto assureDestination) {
		
		Map<String, String> errors = new HashMap<String, String>();
		int errorIndex = -1;
		
		if (assureSource.getIu() == null || assureDestination.getIu() == null) {
			errors.put(String.valueOf(errorIndex), "identifiant_source_destination_non_defini");
			errorIndex--;
		}else {
			if(assureSource.getIu().compareTo(assureDestination.getIu()) != 0) {
				errors.put(String.valueOf(errorIndex), "identifiants_assuresource_assuredestination_non_conformes");
				errorIndex--;
			}
			if(  (assureSource.getRegCod().compareTo(assureDestination.getRegCod()) != 0) && (assureDestination.getRegCod()!=800) ) {
				errors.put(String.valueOf(errorIndex), "regimes_assuresource_assuredestination_non_conformes");
				errorIndex--;
			}
		}
		
		
		return errors;
	}
	//-----
	
	
	
	//-----
	@Override
	public List<CarriereAssure> loadCarriereByAssureMatriculeCle(Integer assMat, Short assCle) {
		return this.loadCarriereByAssureMatriculeCleTrimDepartTrimFin(assMat, assCle, (short)1, (short)1900, (short)1, (short)2999);
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> processTransfertMatricule(AssureDto assureSource, AssureDto assureDestination, String matriculeAgent, Structure structure) {
		Map<String, String> errors = this.checkTransfertMatricule(assureSource, assureDestination);
		
		if(errors.isEmpty()) {
			RcTransfertMatricule rcTransfertMatricule = new RcTransfertMatricule();
			rcTransfertMatricule.setAssMatSource(assureSource.getAssMat());
			rcTransfertMatricule.setAssCleSource(assureSource.getAssCle());
			rcTransfertMatricule.setAssMatDestination(assureDestination.getAssMat());
			rcTransfertMatricule.setAssCleDestination(assureDestination.getAssCle());
			rcTransfertMatricule.setMatriculeAgent(Integer.parseInt(matriculeAgent));
			rcTransfertMatricule.setBrSaisie(structure.getBurCod());
			transfertMatriculeRepository.saveAndFlush(rcTransfertMatricule);
		}
		
		return errors;
	}
	//-----
	
	
	
	//-----
	@Override
	public Short findTauxRachatByDateRef(Short ageDateDemande) {
		TauxRachat tr = tauxRachatRepository.findTauxByAge(ageDateDemande);
		return tr.getTaux();
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> checkAssureCanDoRachat(AssureDto assure) {
		Map<String, String> errors = new HashMap<String, String>();
		
		int errorIndex = -1;
		if (assure.getRegCod()!=320 && assure.getRegCod()!=340 && assure.getRegCod()!=350 && assure.getRegCod()!=345) {
			errors.put(String.valueOf(errorIndex), "regime_service_non_conformes");
			errorIndex--;
		}
		
		return errors;
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> checkRachat(AssureDto assure, Date dateDemande, Short trimestreDebut, Short anneeDebut, Short trimestreFin, Short anneeFin) {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		int errorIndex = -1;
		
		// Trimestre départ supérieure à trimestre fin
		if( (anneeDebut > anneeFin) || (anneeFin.compareTo(anneeDebut)==0 && trimestreDebut>trimestreFin) ) {
			errors.put(String.valueOf(errorIndex), "periode_depart_superieure_periode_fin");
			errorIndex--;
		}
		
		// Date demande dupérieure à date du jour
		if(dateDemande.after(new Date())) {
			errors.put(String.valueOf(errorIndex), "date_demande_superieure_date_du_jour");
			errorIndex--;
		}
		
		// Calcul date fin période selon trimestre fin période 
		java.util.Calendar c = Calendar.getInstance();
		try {
			c.setTime(ApplicationUtil.trimestreToDate(trimestreFin, anneeFin));
		} catch (ParseException e) {
			errors.put(String.valueOf(errorIndex), "erreur_date");
			errorIndex--;
		}
		c.add(Calendar.MONTH, 3);
		c.add(Calendar.DATE, -1);
		Date dateFinPeriode = c.getTime();
		
		// Date fin période ne doit pas dépasser la date effet
		if(dateFinPeriode.after(assure.getAssDteff())) {
			errors.put(String.valueOf(errorIndex), "date_fin_periode_superieure_date_effet");
			errorIndex--;
		}
		
		// Date demande ne doit pas précéder date effet
		if(dateDemande.before(assure.getAssDteff())) {
			errors.put(String.valueOf(errorIndex), "date_demande_precede_date_effet");
			errorIndex--;
		}
		
		// Calcul age % date dépot demande
		int ageDateDemande =
		Period.between(
				assure.getBenDtnais().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
				dateDemande.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
		).getYears();
		
		// age à la date dépot dépot demande ne doit pas dépasser l'age légal d'acceptation (63 ans)
		if(ageDateDemande > 63) {
			errors.put(String.valueOf(errorIndex), "age_assure_date_demande_sup_age_legal");
			errorIndex--;
		}
		
		// Date fin période ne doit pas précéder date dépot demande de plus de 2 ans
		if (
				(ApplicationUtil.getDiffYearsSup(dateFinPeriode, dateDemande)[2] > 2)
				||
				(
						(ApplicationUtil.getDiffYearsSup(dateFinPeriode, dateDemande)[2] == 2)
						&&
						(
								(ApplicationUtil.getDiffYearsSup(dateFinPeriode, dateDemande)[0] > 0)
								||
								(ApplicationUtil.getDiffYearsSup(dateFinPeriode, dateDemande)[1] > 0)
						)
				)
		) {
			errors.put(String.valueOf(errorIndex), "date_depot_depasse_periode_legale_date_demande");
			errorIndex--;
		}
		
		return errors;
		
	}
	//-----
	
	
	
	//-----
	@Override
	public RachatDto processRachat(RachatDto rachat) {
		
		StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("referentiel.p_calcul_salaire@prod_prods");
		// set parameters
		storedProcedure.registerStoredProcedureParameter("Regime", Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("Code_Exploitation", Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("Classe", Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("NbMois", Integer.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("DateEffet", Date.class, ParameterMode.IN);
		storedProcedure.registerStoredProcedureParameter("Salaire", Integer.class, ParameterMode.OUT);
		storedProcedure.registerStoredProcedureParameter("Cotisation", Integer.class, ParameterMode.OUT);
		storedProcedure.setParameter("Regime", (int)rachat.getAssure().getRegCod());
		storedProcedure.setParameter("Code_Exploitation", 0);
		storedProcedure.setParameter("Classe", (int)rachat.getAssure().getCategorieTns());
		storedProcedure.setParameter("NbMois", 3);
		storedProcedure.setParameter("DateEffet", rachat.getDateDemande());
		// execute SP
		storedProcedure.execute();
		// get result
		rachat.setSalaireTrim((Integer)storedProcedure.getOutputParameterValue("Salaire"));
		rachat.setCotisationTrimestriel(  (rachat.getSalaireTrim()*(int) (rachat.getTauxRachat()))/100    );
		
		try {
			Date dateTrimDebut = ApplicationUtil.trimestreToDate(rachat.getTrimestreDebut(), rachat.getAnneeDebut());
			Date dateTrimFin = ApplicationUtil.trimestreToDate(rachat.getTrimestreFin(), rachat.getAnneeFin());
			Period diff = Period.between(
					dateTrimDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), 
					dateTrimFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
			);
			rachat.setNombreDeTrimestres(    (short)    ( (diff.getYears()*4) + (diff.getMonths()/3) + 1 ));
			//rachat.setTotalCotisationRachat((rachat.getSalaireTrim()) * ( (int) (rachat.getNombreDeTrimestres())) *  ( (int) (rachat.getTauxRachat())));
			//rachat.setTotalCotisationRachat(rachat.getTotalCotisationRachat()/100);
			rachat.setTotalCotisationRachat(rachat.getCotisationTrimestriel()* ( (int) rachat.getNombreDeTrimestres())  );
		} catch (ParseException e) {
			return null;
		}
		
		return rachat;
	}
	//-----
	
	
	
	//-----
	@Override
	public List<RcJugement> loadListJugementByAssure(Integer assMat, Short assCle) {
		return jugementRepository.findByAssure(assMat, assCle);
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> processJugement(RcJugement jugement) {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		int errorIndex = -1;
		if (jugement.getEmployeur() == null) {
			errors.put(String.valueOf(errorIndex), "empoyeur_inexistant");
			errorIndex--;
		}
		
		if(errors.isEmpty()) {
			jugementRepository.saveAndFlush(jugement);
		}
		
		return errors;
	}
	//-----
	
	
	
	//-----
	@Override
	public void processDeleteJugement(RcJugement jugement) {
		jugementRepository.delete(jugement);
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> checkAssureSalarie/*Actif*/(AssureDto assure) {
		
		Map<String, String> errors = new HashMap<String, String>();
		int errorIndex = -1;
		
		if(
				assure.getRegCod().compareTo( (short)100 ) != 0 &&
				assure.getRegCod().compareTo( (short)270 ) != 0 &&
				assure.getRegCod().compareTo( (short)280 ) != 0 &&
				assure.getRegCod().compareTo( (short)151 ) != 0 &&
				assure.getRegCod().compareTo( (short)152 ) != 0 &&
				assure.getRegCod().compareTo( (short)153 ) != 0 &&
				assure.getRegCod().compareTo( (short)154 ) != 0 &&
				assure.getRegCod().compareTo( (short)155 ) != 0 &&
				assure.getRegCod().compareTo( (short)156 ) != 0 &&
				assure.getRegCod().compareTo( (short)157 ) != 0 &&
				assure.getRegCod().compareTo( (short)800 ) != 0
				
		) {
			errors.put(String.valueOf(errorIndex), "regime_service_non_conformes");
			errorIndex--;
		}
		return errors;
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> checkAssureActifTrimestre(AssureDto assure, Short trimestre, Short annee) {
		Map<String, String> errors = new HashMap<String, String>();
		int errorIndex = -1;
		Query query = em.createNativeQuery("select referentiel.f_sit_ass_trim@prod_prods(:ass_mat, :ass_cle, :trimestre, :annee) from dual");
		query.setParameter("ass_mat", assure.getAssMat());
		query.setParameter("ass_cle", assure.getAssCle());
		query.setParameter("trimestre", trimestre);
		query.setParameter("annee", annee);
		if( ((BigDecimal) query.getSingleResult()).compareTo(BigDecimal.valueOf(0)) != 0) {
			errors.put(String.valueOf(errorIndex), "assure_non_actif_dans_trimestre");
			errorIndex--;
		}
		return errors;
	}
	//-----
	
	
	
	//-----
	@Override
	public void processDeleteSalaireEstimatif(RcSalaireEstimatif rcSalaireEstimatif) {
		salaireEstimatifRepository.delete(rcSalaireEstimatif);
	}
	//-----
	
	
	
	//-----
	@Override
	public RcTransfertMatricule findTransfertNonExploiteeByAssuresSourceDestination(AssureDto assureSource, AssureDto assureDestination) {
		return transfertMatriculeRepository
				.findOneByAssMatSourceAndAssCleSourceAndAssMatDestinationAndAssCleDestination
				(
						assureSource.getAssMat(),
						assureSource.getAssCle(),
						assureDestination.getAssMat(),
						assureDestination.getAssCle()
				);
	}
	//-----
	
	
	
	//-----
	@Override
	public void processCancelTransfertMatricule(RcTransfertMatricule transfert) {
		transfertMatriculeRepository.delete(transfert);
	}	
	//-----	
	
	
	
	//-----
	@Override
	public List<RcDg> loadFromCarriereByAssure(Integer assMat, Short assCle) {
		List<CarriereAssure> carriere = carriereAssureRepository.findByIdAssMatAndIdAssCle(assMat, assCle);
		List<RcDg> carriereToProcess = new ArrayList<RcDg>();
		for (CarriereAssure ligneCarriere : carriere) {
			if (
					ligneCarriere.getId().getRegime().compareTo( (short)100 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)270 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)280 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)151 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)152 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)153 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)154 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)155 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)156 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)157 ) == 0 ||
					ligneCarriere.getId().getRegime().compareTo( (short)800 ) == 0
					
			) {
				RcDg ligneDg = new RcDg();
				ligneDg.setAssMat(assMat);
				ligneDg.setAssCle(assCle);	
				ligneDg.setTrimestre(ligneCarriere.getId().getTrimestre());
				ligneDg.setAnnee(ligneCarriere.getId().getAnnee());
				ligneDg.setTypeSalaire(typeSalaireRepository.findById(ligneCarriere.getId().getTypeSalaire()).orElse(null));
				ligneDg.setEmpMat(ligneCarriere.getEmpMat());
				ligneDg.setEmpCle(ligneCarriere.getEmpCle());
				ligneDg.setSalaire(ligneCarriere.getSalaire());
				ligneDg.setDateSaisie(ligneCarriere.getDateSaisie());
				carriereToProcess.add(ligneDg);
			}
		}		
		return carriereToProcess;
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> checkLigne(RcDg rcDg) {
		List<CarriereAssure> carriereBeforeInsertEdit = carriereAssureRepository.findByIdAssMatAndIdAssCle(rcDg.getAssMat(), rcDg.getAssCle());
		Map<String, String> errors = new HashMap<String, String>();
		
		int errorIndex = -1;
		
		if (rcDg.getEmpMat()==null || rcDg.getEmpCle()==null || rcDg.getTrimestre()==null || rcDg.getAnnee()==null || rcDg.getTypeSalaire()== null || rcDg.getSalaire()==null) {
			errors.put(String.valueOf(errorIndex), "donnees_incompletes");
			errorIndex--;
			return errors;
		}
		
		Employeur employeur = null;
		if(rcDg.getEmpMat() != 999999) {
			employeur = employeurRepository.findById(new EmployeurId(rcDg.getEmpMat(), rcDg.getEmpCle())).orElse(null);
		}
		AssureDto assure = assureService.findAssure(rcDg.getAssMat(), rcDg.getAssCle());
		
		Date dateTrimestre = null;
		try {
			dateTrimestre = ApplicationUtil.trimestreToDate(rcDg.getTrimestre(), rcDg.getAnnee());
		}catch(ParseException e) {
			
		}	
		
		// On vérifie si on est en mode insertion ou modification		
		if(rcDg.getTypeOperation().compareTo("I") == 0) { // On est en mode insertion			
			// Vérifier qu'une autre ligne avec le même couple assuré/employeur/trimestre/année/type salaire n existe pas
			if(carriereBeforeInsertEdit
					.stream()
					.filter(
						s -> 	s.getId().getAssMat().compareTo(rcDg.getAssMat()) == 0
								&&
								s.getId().getAssCle().compareTo(rcDg.getAssCle()) == 0
								&&
								s.getId().getTrimestre().compareTo(rcDg.getTrimestre()) == 0
								&&
								s.getId().getAnnee().compareTo(rcDg.getAnnee()) == 0 
								&& 
								s.getEmpMat().compareTo(rcDg.getEmpMat() ) == 0
								&&
								s.getEmpCle().compareTo(rcDg.getEmpCle()) == 0
								&&
								s.getId().getTypeSalaire().compareTo(rcDg.getTypeSalaire().getCodeTypeSalaire()) == 0
					)
					.collect(Collectors.toList())
					.size() > 0
			) {
				errors.put(String.valueOf(errorIndex), "trimestre_carriere_exist");
				errorIndex--;
			}	
			// Vérifier l'existence de l'employeur et la conformité du régime employeur <=> assuré
			if(employeur == null && rcDg.getEmpMat() != 999999) {
				errors.put(String.valueOf(errorIndex), "empoyeur_inexistant");
				errorIndex--;
			} else {
				if (rcDg.getEmpMat() != 999999) {
					if ( String.format("%1$08d", assure.getAssMat()).substring(0, 1).compareTo("8") !=0 ) {
						if( (employeur.getRegime().compareTo(assure.getRegCod()) != 0) && ((int)assure.getRegCod()!=800)) {
							errors.put(String.valueOf(errorIndex), "regimes_employeur_assure_non_conformes");
							errorIndex--;
						}	
					} else { // Cas 32/2002
						if (
								employeur.getRegime()!=151 &&
								employeur.getRegime()!=152 &&
								employeur.getRegime()!=153 &&
								employeur.getRegime()!=154 &&
								employeur.getRegime()!=155 &&
								employeur.getRegime()!=156 &&
								employeur.getRegime()!=157 &&
								employeur.getRegime()!=280 
						) {
							errors.put(String.valueOf(errorIndex), "regimes_employeur_assure_non_conformes");
							errorIndex--;
						}
					}
					if (dateTrimestre.after(employeur.getEmpDteff())) {
						// Vérifier que l'employeur est actif au cours du trimestre en question
						Query query = em.createNativeQuery("select referentiel.f_sit_emp_trim@prod_prods(:emp_mat, :emp_cle, :trimestre, :annee) from dual");
						query.setParameter("emp_mat", rcDg.getEmpMat());
						query.setParameter("emp_cle", rcDg.getEmpCle());
						query.setParameter("trimestre", rcDg.getTrimestre());
						query.setParameter("annee", rcDg.getAnnee());
						if( ((BigDecimal) query.getSingleResult()).compareTo(BigDecimal.valueOf(0)) != 0) {
							errors.put(String.valueOf(errorIndex), "employeur_non_actif_dans_trimestre");
							errorIndex--;
						}
					}
				} else {
					if ( String.format("%1$08d", assure.getAssMat()).substring(0, 1).compareTo("8") == 0 && rcDg.getAnnee()>=2002) {
						errors.put(String.valueOf(errorIndex), "empoyeur_inexistant");
						errorIndex--;
					}
				}
			}			
			// Vérifier que le trimestre ne dépasse pas le trimestre précédent le trimestre de la date du jour pour les types salaires autres que 13 et trimestre correspondant à date du jour pour le type 13
			if (rcDg.getTypeSalaire().getCodeTypeSalaire().compareTo((short) 13) != 0) {
				LocalDate dtPreviousTrim = LocalDate.now().minusMonths(3);
				if( 
						( ((int)rcDg.getAnnee()) > dtPreviousTrim.getYear() ) 
						||  
						(
								( ((int)rcDg.getAnnee()) == dtPreviousTrim.getYear() )
								&&
								( ((int)rcDg.getTrimestre()) > dtPreviousTrim.get(IsoFields.QUARTER_OF_YEAR)) )
						) {
					errors.put(String.valueOf(errorIndex), "trimestre_depasse_trimestre_de_cotisation");
					errorIndex--;
				}		
			}else {
				LocalDate dtPreviousTrim = LocalDate.now();
				if( 
						( ((int)rcDg.getAnnee()) > dtPreviousTrim.getYear() ) 
						||  
						(
								( ((int)rcDg.getAnnee()) == dtPreviousTrim.getYear() )
								&&
								( ((int)rcDg.getTrimestre()) > dtPreviousTrim.get(IsoFields.QUARTER_OF_YEAR)) )
						) {
					errors.put(String.valueOf(errorIndex), "trimestre_depasse_trimestre_de_cotisation");
					errorIndex--;
				}		
			}
				
			// Vérifier que l'assuré est actif au cours du trimestre en question si trimestre supérieur à date effet pour les types salaires autres que 13
			if (dateTrimestre.after(assure.getAssDteff()) && rcDg.getTypeSalaire().getCodeTypeSalaire()!=13) {
				Query query = em.createNativeQuery("select referentiel.f_sit_ass_trim@prod_prods(:ass_mat, :ass_cle, :trimestre, :annee) from dual");
				query.setParameter("ass_mat", rcDg.getAssMat());
				query.setParameter("ass_cle", rcDg.getAssCle());
				query.setParameter("trimestre", rcDg.getTrimestre());
				query.setParameter("annee", rcDg.getAnnee());
				// Situation dans trimestre
				BigDecimal situationTrimestre = (BigDecimal) query.getSingleResult();				
				if (situationTrimestre.compareTo(BigDecimal.valueOf(0)) != 0) {					
					errors.put(String.valueOf(errorIndex), "assure_non_actif_dans_trimestre");
					errorIndex--;
				}
			}
		}		
		//  Salaire ICP => trimestre doit être entre 1/1988 et 1/1990
		if (
				rcDg.getTypeSalaire().getCodeTypeSalaire().compareTo( (short)2 ) == 0
				&&
				(
						rcDg.getAnnee()<1988
						||
						rcDg.getAnnee()>1990
						||
						(rcDg.getAnnee()==1990 && rcDg.getTrimestre()>1)
				)
		) {
			{
				errors.put(String.valueOf(errorIndex), "type_salaire_trimestre_non_conformes");
				errorIndex--;
			}
		}
		// Prime de pensionné => année >= 2010
		if (
				rcDg.getTypeSalaire().getCodeTypeSalaire().compareTo( (short)13 ) == 0
				&&
				rcDg.getAnnee() < 2010
		) {
			{
				errors.put(String.valueOf(errorIndex), "type_salaire_trimestre_non_conformes");
				errorIndex--;
			}
		}
		return errors;
	}
	//-----
	
	
	
	//-----
	/*
	@Override
	public List<RcDemandeDg> findAllDemandesDg() {
		return rcDemandeDgRepository.findAll();
	}
	*/
	//-----
	
	
	
	//-----
	@Override
	public RcDemandeDg findDemandeById(Long idDemande) {
		return rcDemandeDgRepository.findById(idDemande).orElse(null);
	}
	//-----
	
	
	
	//-----
	@Override
	public List<RcDemandeDgObjet> findAllObjetsDemandes() {
		return rcDemandeDgObjetRepository.findAll();
	}
	//-----
	
	
	
	//-----
	@Override
	public Map<String, String> addDemande(RcDemandeDg demandeDg, Structure structure, Integer matAgent) {
		// Vérifier si une demande en cours existe pour le même assuré
		/*
		if (rcDemandeDgRepository.findByAssure(demandeDg.getAssure()).size()>0) {
		}
		*/
		Map<String, String> errors = new HashMap<String,String>();
		int errorIndex = -1;
		for (RcDemandeDg d : rcDemandeDgRepository.findByAssure(demandeDg.getAssure())) {
			/*
			if (d.getSituationActuelle().getSituation().getCode().compareTo((short) 4)!=0) {
				errors.put(String.valueOf(errorIndex), "demande_en_cours_assure_existe".concat(";;;").concat(d.getBureau().getBurCod().toString().concat("-").concat(d.getBureau().getBurIntitAr())));
			}
			*/
			if (d.getHistoriqueSituation().get(d.getHistoriqueSituation().size()-1).getSituation().getCode().compareTo((short) 4)!=0) {
				errors.put(String.valueOf(errorIndex), "demande_en_cours_assure_existe".concat(";;;").concat(d.getBureau().getBurCod().toString().concat("-").concat(d.getBureau().getBurIntitAr())));
			}
		}
		AssureDto assureDto = assureService.findAssure(demandeDg.getAssure().getId().getAssMat(), demandeDg.getAssure().getId().getAssCle());
		//errors.putAll(checkAssureSalarie(assureDto));
		if (errors.isEmpty()) {
			RcDemandeDgHistoriquSituation s = new RcDemandeDgHistoriquSituation();
			demandeDg.setBureau(structure);
			s.setDateSituation(new Date());
			s.setDemandeDg(demandeDg);
			s.setSituation(rcDemandeDgSituationRepository.findById( (short)1 ).orElse(null));
			s.setMatriculeAgent(matAgent);
			demandeDg.setHistoriqueSituation(Arrays.asList(s));
			rcDemandeDgRepository.saveAndFlush(demandeDg);
		}
		return errors;
	}
	//-----
	
	
	
	//-----
	@Override
	public void deleleDemande(RcDemandeDg demande) {
		rcDemandeDgRepository.delete(demande);
	}
	//-----

	

	//-----
	@Override
	public List<RcDemandeDg> findAllDemandesDgByStructure(Structure structure) {
		return rcDemandeDgRepository.findByBureau(structure);
	}
	//-----
	
	
	
	//-----
	@Override
	public List<RcDemandeDgDto> findAllDemandesByStructure(Structure structure) {
		return rcDemandeDgRepository.findAllDemandesByBureau(structure.getBurCod());
	}
	//-----
	
	
	
	//-----
	@Override
	@Transactional
	public Map<String, String> processDg(List<RcDg> carriereToProcess, Structure structure, Integer matriculeAgent, RcDemandeDg demandeDg) { 
		Map<String, String> errors = new HashMap<String,String>();
		for(RcDg ligne: carriereToProcess) {
			ligne.setDemandeDg(demandeDg);
			if (ligne.getTypeOperation()!=null) {
				ligne.setDateSaisie(new Date());
				ligne.setDateExploitation(null);
				ligne.setBrSaisie(structure.getBurCod());
				ligne.setAgent(matriculeAgent);
				if (ligne.getTypeOperation()=="I") {
					Map<String, String> errorsTrim = checkLigne(ligne);
					for (Map.Entry<String, String> error : errorsTrim.entrySet()) {
						errors.put(error.getKey(), error.getValue()+"_trimestre"+";;;"+ligne.getTrimestre()+"/"+ligne.getAnnee());
					}
				}
			}
		}	
		if (errors.isEmpty()) {
			rcDgRepository.saveAll(carriereToProcess);
			// changer situation demande
			RcDemandeDgHistoriquSituation s = new RcDemandeDgHistoriquSituation();
			s.setMatriculeAgent(matriculeAgent);
			s.setSituation(rcDemandeDgSituationRepository.findById((short) 2).orElse(null));
			s.setDateSituation(new Date());
			s.setDemandeDg(demandeDg);
			demandeDg.getHistoriqueSituation().add(s);
			rcDemandeDgRepository.saveAndFlush(demandeDg);	
		}
		return errors;
	}
	//-----
	
	
	/*
	boolean toCheck = true;
			
		if (rcDg.getTypeOperation()==null) { // Update
			if (salaireBeforUpdate.compareTo(rcDg.getSalaire())!=0) {
				rcDg.setOldSalaire(salaireBeforUpdate);
				rcDg.setTypeOperation("U");
			}else {
				toCheck = false;
			}
		}
		if (toCheck) {
			Map<String, String> errors = carriereService.checkLigne(rcDg);
			if(!errors.isEmpty()) {
				ApplicationUtil.displayErrors(errors);
				FacesContext.getCurrentInstance().validationFailed();
			}else {
				PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').removeAttribute('disabled');");
		    	PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').setAttribute('aria-disabled', 'false');");
		    	PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').classList.remove('ui-state-disabled');");
			}
		}	 
	*/
	
	
	//-----
	@Override
	public List<RcDg> loadFromDgByDemande(RcDemandeDg demandeDg) {
		return rcDgRepository.findByDemandeDg(demandeDg);
	}
	//-----
	
	
	
	//-----
	@Override
	public void validerDemandeDg(RcDemandeDg demandeDg, Integer matriculeAgent) {
		RcDemandeDgHistoriquSituation s = new RcDemandeDgHistoriquSituation();
		s.setDateSituation(new Date());
		s.setMatriculeAgent(matriculeAgent);
		s.setSituation(rcDemandeDgSituationRepository.findById((short) 3).orElse(null));
		s.setDemandeDg(demandeDg);
		demandeDg.getHistoriqueSituation().add(s);
		rcDemandeDgRepository.saveAndFlush(demandeDg);
	}
	//-----
	
	
	
	//-----
	@Override
	public void generateRachatOrdrePaiement(RachatDto rachat, Structure structure, String matriculeAgent) {
		opcProdRepository.createRachatOrdrePaiement(rachat, structure, matriculeAgent);
	}
	//-----
	
	
	
	//-----
	@Override
	public List<RcDemandeDgHistoriquSituation> findHistoriqueDemande(RcDemandeDgDto demandeDto) {
		RcDemandeDg rcDemandeDg = rcDemandeDgRepository.findById(demandeDto.getId()).orElse(rcDemandeDg = null);
		return rcDemandeDgHistoriqueSituationRepository.findByDemandeDg(rcDemandeDg);
	}
	//--------------------------------------------------------------------------------------------------------------
	
	
}
