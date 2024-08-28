package tn.nat.cnss.reconstitutioncarriere.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import tn.nat.cnss.reconstitutioncarriere.model.CarriereAssure;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDg;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgHistoriquSituation;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgObjet;
import tn.nat.cnss.reconstitutioncarriere.model.RcDg;
import tn.nat.cnss.reconstitutioncarriere.model.RcJugement;
import tn.nat.cnss.reconstitutioncarriere.model.RcSalaireEstimatif;
import tn.nat.cnss.reconstitutioncarriere.model.RcTransfertMatricule;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
//import tn.nat.cnss.reconstitutioncarriere.model.dto.LigneCarriereDto;
import tn.nat.cnss.reconstitutioncarriere.model.dto.RachatDto;
import tn.nat.cnss.reconstitutioncarriere.model.dto.RcDemandeDgDto;
import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;

public interface CarriereService {

	public Map<String, String> processSalaireEstimatif(RcSalaireEstimatif rcSalaireEstimatif);

	public RcSalaireEstimatif findSalaireEstimatifByAssure(Integer assMat, Short assCle);

	public List<CarriereAssure> loadCarriereByAssureMatriculeCleTrimDepartTrimFin(Integer assMat, Short assCle, Short trimestreDepart, Short anneeDepart, Short trimestreFin, Short anneeFin);

	public void processTransfertCnrps(List<CarriereAssure> listSalairesPourTransfert, String matriculeAgent, Structure structure);

	public Map<String, String> checkTransfertMatricule(AssureDto assureSource, AssureDto assureDestination);

	public List<CarriereAssure> loadCarriereByAssureMatriculeCle(Integer assMat, Short assCle);

	public Map<String, String> processTransfertMatricule(AssureDto assureSource, AssureDto assureDestination, String matriculeAgent, Structure structure);

	public Short findTauxRachatByDateRef(Short ageDateDemande);

	public Map<String, String> checkAssureCanDoRachat(AssureDto assure);

	public Map<String, String> checkRachat(AssureDto assure, Date dateDemande, Short trimestreDebut, Short anneeDebut, Short trimestreFin, Short anneeFin);

	public RachatDto processRachat(RachatDto rachat);

	public List<RcJugement> loadListJugementByAssure(Integer assMat, Short assCle);

	public Map<String, String> processJugement(RcJugement jugement);

	public void processDeleteJugement(RcJugement jugement);

	public Map<String, String> checkAssureSalarie(AssureDto assure);

	public void processDeleteSalaireEstimatif(RcSalaireEstimatif rcSalaireEstimatif);

	public Map<String, String> checkAssureActifTrimestre(AssureDto assure, Short trimestre, Short annee);

	public RcTransfertMatricule findTransfertNonExploiteeByAssuresSourceDestination(AssureDto assureSource, AssureDto assureDestination);

	public void processCancelTransfertMatricule(RcTransfertMatricule transfert);

	public List<RcDg> loadFromCarriereByAssure(Integer assMat, Short assCle);

	public Map<String, String> checkLigne(RcDg rcDg);

	public RcDemandeDg findDemandeById(Long idDemande);

	public List<RcDemandeDgObjet> findAllObjetsDemandes();

	public Map<String, String> addDemande(RcDemandeDg demandeDg, Structure structure, Integer matAgent);

	public void deleleDemande(RcDemandeDg demande);

	public List<RcDemandeDg> findAllDemandesDgByStructure(Structure structure);

	public Map<String, String> processDg(List<RcDg> carriereToProcess, Structure structure, Integer matriculeAgent, RcDemandeDg demandeDg);

	public List<RcDg> loadFromDgByDemande(RcDemandeDg demandeDg);

	public void validerDemandeDg(RcDemandeDg demandeDg, Integer matriculeAgent);

	public void generateRachatOrdrePaiement(RachatDto rachat, Structure structure, String matriculeAgent);

	public List<RcDemandeDgDto> findAllDemandesByStructure(Structure structure);

	public List<RcDemandeDgHistoriquSituation> findHistoriqueDemande(RcDemandeDgDto demandeDto);


}
