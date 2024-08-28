package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import tn.nat.cnss.reconstitutioncarriere.model.Employeur;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDg;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgSituation;
import tn.nat.cnss.reconstitutioncarriere.model.RcDg;
import tn.nat.cnss.reconstitutioncarriere.model.TypeSalaire;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.repository.TypeSalaireRepository;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;
import tn.nat.cnss.reconstitutioncarriere.service.CarriereService;
import tn.nat.cnss.reconstitutioncarriere.util.ApplicationUtil;

@ManagedBean(name = "rcDgController")
@ViewScoped
public class RcDgController extends SpringBeanAutowiringSupport implements Serializable{
	
	private static final long serialVersionUID = -7214708464595187823L;
	
	private Integer assMat;
	private Short assCle;
	private Short annee;
	private AssureDto assure;
	private List<TypeSalaire> listTypesSalaire;
	private List<RcDg> carriereToProcess;
	private List<RcDg> filteredCarriereToProcess;
	private RcDg objectToProcess;
	private Long salaireBeforUpdate;
	
	private Integer empMat;
	private Short empCle;
	private Employeur employeur;
	private List<RcDg> ligneDgList;
	private RcDemandeDg demandeDg;
	private String modeAcces;
	//private boolean inserting = false;
	private String listTrimestresToShow = "1";
	
	@Autowired
	private AssureService assureService;
	
	@Autowired
	private CarriereService carriereService;
	
	@Autowired
	private TypeSalaireRepository typeSalaireRepository;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	private Boolean actionsDisabled;
	
	

	
	
	
	//private static final Logger logger = LoggerFactory.getLogger(RcDgController.class);
	private static final Logger logger = LogManager.getLogger(RcDgController.class);
			
	
	
	//--------------------------------------------------------------------------------
	public void prenderViewChecks(ComponentSystemEvent event) throws IOException {	
		if (!FacesContext.getCurrentInstance().isPostback()) {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, String> params = ec.getRequestParameterMap();
			if (params.get("demandeId") == null) {
				ec.redirect("dg_liste_demandes.xhtml?faces-redirect=true");
				return;
			}
			if (demandeDg == null) {
				ec.redirect("dg_liste_demandes.xhtml?faces-redirect=true");
				return;
			}
			if (params.get("modeAcces") == null) {
				ec.redirect("dg.xhtml?demandeId="+demandeDg.getId()+"&modeAcces=view");
			}
			// en attente de validation et mode accès  <> validation/view => forcer view
			RcDemandeDgSituation situationActuelle = demandeDg.getHistoriqueSituation().get(demandeDg.getHistoriqueSituation().size()-1).getSituation(); 
			if(
					situationActuelle.getCode().compareTo((short) 2)==0
					&&
					modeAcces.compareTo("validate")!=0
					&&
					modeAcces.compareTo("view")!=0
					&&
					modeAcces.compareTo("edit")!=0
			) {
				//setModeAcces("view");
				ec.redirect("dg.xhtml?demandeId="+demandeDg.getId()+"&modeAcces=view");
			}
			// en attente d exploitation => forcer view
			if(
					situationActuelle.getCode().compareTo((short) 3)==0
					&&
					modeAcces.compareTo("view")!=0
			) {
				//setModeAcces("view");
				ec.redirect("dg.xhtml?demandeId="+demandeDg.getId()+"&modeAcces=view");
			}
			// demande n appartient pas à la structure en cours
			if (demandeDg.getBureau().getBurCod().compareTo(sessionBean.getUserConnectedAs().getBurCod())!=0) {
				ec.redirect("dg_liste_demandes.xhtml?faces-redirect=true");
			}
		}
	}	
	//
	@PostConstruct
	public void init() {
		Map<String, String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(params.get("demandeId") != null) {
			demandeDg = carriereService.findDemandeById(Long.parseLong(params.get("demandeId")));
			if (demandeDg != null) {
				setAssMat(demandeDg.getAssure().getId().getAssMat());
				setAssCle(demandeDg.getAssure().getId().getAssCle());
				setAssure(assureService.findAssure(assMat, assCle));
			}
		}
		if(params.get("modeAcces") != null) {
			modeAcces = (params.get("modeAcces"));
		}else {
			modeAcces = "view";
		}
		listTypesSalaire = typeSalaireRepository.findAllById(Arrays.asList((short)0, (short)1, (short)2, (short)3,(short)4, (short)5, (short)6, (short)13));
		refreshAssure();
	}
	//-----
	public void refreshAssure() {
		carriereToProcess = new ArrayList<RcDg>();
		setAnnee(null);
		if(getAssMat() != null && getAssCle() != null) {
			Map<String, String> errors = assureService.checkCleAssure(assMat, assCle);
			if(!errors.isEmpty()) {
				ApplicationUtil.displayErrors(errors);
				FacesContext.getCurrentInstance().validationFailed();
				setAssure(null);
			}else {
				// Assuré
				setAssure(assureService.findAssure(assMat, assCle));
				if(getAssure() != null ) {
					/*
					errors = carriereService.checkAssureSalarie(assure);
					if(!errors.isEmpty()) {
						ApplicationUtil.displayErrors(errors);
						FacesContext.getCurrentInstance().validationFailed();
						setAssure(null);
					} else {*/
						RcDemandeDgSituation situationActuelle = demandeDg.getHistoriqueSituation().get(demandeDg.getHistoriqueSituation().size()-1).getSituation(); 
						if (modeAcces.compareTo("edit")==0) {
							if (situationActuelle.getCode().compareTo((short) 1)==0) {
								carriereToProcess = carriereService.loadFromCarriereByAssure(assMat, assCle).stream().filter(s -> s.getAssMat().compareTo(assMat)==0).collect(Collectors.toList());
							}	
							if (situationActuelle.getCode().compareTo((short) 2)==0) {
								carriereToProcess = carriereService.loadFromDgByDemande(demandeDg);
							}	
						}
						if (modeAcces.compareTo("view")==0 || modeAcces.compareTo("validate")==0) {
							carriereToProcess = carriereService.loadFromDgByDemande(demandeDg);
						}
						filteredCarriereToProcess = carriereToProcess;
						refreshCarriere();
					/*}*/
				} else {
					ApplicationUtil.displayError("assure_inexistant", null);
				}
			}
		}else {
			setAssure(null);
		}
		
		//carriereToProcess = carriereToProcess.stream().filter(c -> c.getEmpMat() != null).collect(Collectors.toList());
		//filteredCarriereToProcess = carriereToProcess;
	}	
	//-----
	public void refreshCarriere() {
		filteredCarriereToProcess = new ArrayList<RcDg>();
		if(getAssure() != null ) {
			filteredCarriereToProcess = carriereToProcess;
			Comparator<Short> nullSafeShortComparator = Comparator.nullsLast(Short::compareTo);
			Comparator<RcDg> compareDg = Comparator.comparing(RcDg::getAnnee, nullSafeShortComparator).thenComparing(RcDg::getTrimestre, nullSafeShortComparator);				
			filteredCarriereToProcess = filteredCarriereToProcess.stream().sorted(compareDg).collect(Collectors.toList());
			if(getAnnee() != null) {
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				if ( (getAnnee().compareTo( (short) 1960)<0) || (getAnnee().compareTo( (short) currentYear)>0) ) {
					ApplicationUtil.displayError("valeur_min_max_error","a_annee","1960",String.valueOf(currentYear));
				}
				filteredCarriereToProcess = filteredCarriereToProcess.stream().filter(s -> s.getAnnee().compareTo(annee) == 0 && s.getAssMat().compareTo(assMat)==0).collect(Collectors.toList());
			}
			if (getListTrimestresToShow().compareTo("2")==0) {
				filteredCarriereToProcess = filteredCarriereToProcess.stream().filter(s -> s.getTypeOperation()!=null).collect(Collectors.toList());
			}
			
			// Appliquer les styles d affichage
			for (RcDg dg : filteredCarriereToProcess) {
				if (dg.getTypeOperation() != null) {
					if(dg.getTypeOperation().compareTo("I")==0) {
						dg.setStyleInDatatable("highlight-1");
					}
					if(dg.getTypeOperation().compareTo("U")==0) {
						dg.setStyleInDatatable("highlight-2");
					}
					if(dg.getTypeOperation().compareTo("D")==0) {
						dg.setStyleInDatatable("highlight-3");
					}
				}else {
					dg.setStyleInDatatable("ui-widget-content");
				}
			}
		}
	}
	//-----
	public void onRowEdit(RowEditEvent event) {
		
		RcDg rcDg = (RcDg)event.getObject();
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
		//inserting = false;
		
    }
    //-----
    public void initNewLigneCarriere() {    	
    	RcDg rcDg = new RcDg();
    	rcDg.setAnnee(getAnnee());
    	rcDg.setTypeOperation("I");
    	rcDg.setAssMat(assMat);
    	rcDg.setAssCle(assCle);
    	getCarriereToProcess().add(rcDg);    
    	//inserting = true;
    	refreshCarriere();
    }
    //-----
    public void prepareDeleteCancelModifications(RcDg salaire) {    	
    	this.objectToProcess = salaire;
    }
    //-----
    public void processLigneForDelete() {
    	objectToProcess.setTypeOperation("D");    	
    	refreshCarriere();
    }
    //-----
    public void refreshRow(RcDg l) {
    	if (l.getEmpMat() != null) {
    		if(l.getEmpMat()==999999) {
    			l.setEmpCle((short)26);
    		}
    	}
    	if (l.getEmpMat()!=null && l.getEmpCle()!=null) {
	    	if (l.getEmpMat()==329912 && l.getEmpCle()==15) {
	    		l.getTypeSalaire().setCodeTypeSalaire( (short)4 );
	    	}
    	}
    } 
    //-----
    public void saveOldState(RowEditEvent event) {
    	PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').setAttribute('disabled', 'disabled');");
    	PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').setAttribute('aria-disabled', 'true');");
    	PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').classList.add('ui-state-disabled');");    	
    	salaireBeforUpdate = ((RcDg)event.getObject()).getSalaire();
    }
    //-----
    public void cancelLigne() {
    	if (objectToProcess.getTypeOperation()=="U") {
    		objectToProcess.setSalaire(objectToProcess.getOldSalaire());
    		objectToProcess.setOldSalaire(null);
    	}
    	if (objectToProcess.getTypeOperation()=="I") {
    		carriereToProcess.remove(objectToProcess);
    	}
    	objectToProcess.setTypeOperation(null);
    	refreshCarriere();
    }
    //-----
    public void onRowCancel(RowEditEvent event) {
    	RcDg rcDg = (RcDg)event.getObject();
    	if (rcDg.getTypeOperation()=="I" /*&& inserting*/) {
    		carriereToProcess.remove(rcDg);
    	}
    	PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').removeAttribute('disabled');");
    	PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').setAttribute('aria-disabled', 'false');");
    	PrimeFaces.current().executeScript("document.getElementById('frm-dg:btn_add_dg').classList.remove('ui-state-disabled');");
    	refreshCarriere();
    }
    //-----
    public void saveDg() {
    	try {
    		logger.info("saveDG  "+assMat+"-"+assCle+" Done...");	
    		if (carriereToProcess.stream().filter(l -> l.getTypeOperation() != null).count() == 0 ) {
    			ApplicationUtil.displayError("aucun_trimestre_modifie", null);
    			FacesContext.getCurrentInstance().validationFailed();
    			return;
    		}
    		Map<String, String> errors = carriereService.processDg(carriereToProcess, sessionBean.getUserConnectedAs(), Integer.parseInt(sessionBean.getUserMatricule()), demandeDg);;
			if(!errors.isEmpty()) {
				ApplicationUtil.displayErrors(errors);
				FacesContext.getCurrentInstance().validationFailed();
			}else {		   		
	    		setModeAcces("view");    		
	    		ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
			} 
    		logger.info("saveDG  "+assMat+"-"+assCle+" Done...");			
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error("saveDG  "+assMat+"-"+assCle+" Error "+e.getClass());
    		ApplicationUtil.displayError("erreur_non_programmee",null);
    		FacesContext.getCurrentInstance().validationFailed();
    	}
    }
    //-----
    public void cancelDg() {
    	refreshAssure();
    	ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_WARN, "warning", "updateCanceled");
    }
    //-----
    public void validerDemandeDg() {
    	try {
    		carriereService.validerDemandeDg(demandeDg, Integer.parseInt(sessionBean.getUserMatricule()));
    		setModeAcces("view");
    		ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
    	}catch(Exception e) {
    		ApplicationUtil.displayError("erreur_non_programmee",null);
    	}    	
    }
	//--------------------------------------------------------------------------------
	
	
	




	public Integer getAssMat() { 
		return assMat;
	}
	public void setAssMat(Integer assMat) {
		this.assMat = assMat;
	}
	//---
	public Short getAssCle() {
		return assCle;
	}
	public void setAssCle(Short assCle) {
		this.assCle = assCle;
	}
	//---
	public Short getAnnee() {
		return annee;
	}
	public void setAnnee(Short annee) {
		this.annee = annee;
	}
	//---
	public AssureDto getAssure() {
		return assure;
	}
	public void setAssure(AssureDto assure) {
		this.assure = assure;
	}
	//---
	public List<TypeSalaire> getListTypesSalaire() {
		return listTypesSalaire;
	}
	public void setListTypesSalaire(List<TypeSalaire> listTypesSalaire) {
		this.listTypesSalaire = listTypesSalaire;
	}
	//---	
	public Integer getEmpMat() {
		return empMat;
	}
	public void setEmpMat(Integer empMat) {
		this.empMat = empMat;
	}
	//---
	public Short getEmpCle() {
		return empCle;
	}
	public void setEmpCle(Short empCle) {
		this.empCle = empCle;
	}
	//---	
	public Employeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	//---
	public List<RcDg> getLigneDgList() {
		return ligneDgList;
	}
	public void setLigneDgList(List<RcDg> ligneDgList) {
		this.ligneDgList = ligneDgList;
	}
	//---
	/*
	public List<LigneCarriereDto> getCarriere() {
		return carriere;
	}
	public void setCarriere(List<LigneCarriereDto> carriere) {
		this.carriere = carriere;
	}
	//---
	public LigneCarriereDto getToDelete() {
		return toDelete;
	}
	public void setToDelete(LigneCarriereDto toDelete) {
		this.toDelete = toDelete;
	}
	*/
	//---
	public SessionBean getSessionBean() {
		return sessionBean;
	}
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	//---
	public List<RcDg> getCarriereToProcess() {
		return carriereToProcess;
	}
	public void setCarriereToProcess(List<RcDg> carriereToProcess) {
		this.carriereToProcess = carriereToProcess;
	}
	//---
	public List<RcDg> getFilteredCarriereToProcess() {
		return filteredCarriereToProcess;
	}
	public RcDg getObjectToProcess() {
		return objectToProcess;
	}	
	//---
	public void setObjectToProcess(RcDg objectToProcess) {
		this.objectToProcess = objectToProcess;
	}
	public void setFilteredCarriereToProcess(List<RcDg> filteredCarriereToProcess) {
		this.filteredCarriereToProcess = filteredCarriereToProcess;
	}
	//---
	public String getModeAcces() {
		return modeAcces;
	}
	public void setModeAcces(String modeAcces) {
		this.modeAcces = modeAcces;
	}
	//---
	/*
	public boolean isInserting() {
		return inserting;
	}
	public void setInserting(boolean inserting) {
		this.inserting = inserting;
	}
	*/
	//---
	public String getListTrimestresToShow() {
		return listTrimestresToShow;
	}
	public void setListTrimestresToShow(String listTrimestresToShow) {
		this.listTrimestresToShow = listTrimestresToShow;
	}
	//---
	public Boolean getActionsDisabled() {
		return actionsDisabled;
	}
	public void setActionsDisabled(Boolean actionsDisabled) {
		this.actionsDisabled = actionsDisabled;
	}
	

}
