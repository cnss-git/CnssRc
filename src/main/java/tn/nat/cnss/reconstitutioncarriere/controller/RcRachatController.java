package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.model.dto.RachatDto;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;
import tn.nat.cnss.reconstitutioncarriere.service.CarriereService;
import tn.nat.cnss.reconstitutioncarriere.util.ApplicationUtil;


@ManagedBean(name = "rcRachatController")
@ViewScoped
public class RcRachatController extends SpringBeanAutowiringSupport implements Serializable{

	private static final long serialVersionUID = 2652207121281395870L;
	
	private Integer assMat;
	private Short assCle;
	private AssureDto assure;
	private Date dateDemande;
	private Short trimestreDebut;
	private Short anneeDebut;
	private Short trimestreFin;
	private Short anneeFin;
	private Short tauxRachat;
	private Short ageDateDemande;
	private RachatDto rachat;
	private boolean ordrePaiemenentGenerated = false;
	
	@Autowired
	private AssureService assureService;
	
	@Autowired
	private CarriereService carriereService;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	
	//--------------------------------------------------------------------------------		
	//-----
	public void refreshAssure() {
		PrimeFaces.current().resetInputs("frm-rachat:p_rachat");	
		if(getAssMat() != null && getAssCle() != null) {			
			Map<String, String> errors = assureService.checkCleAssure(assMat, assCle);
			if(!errors.isEmpty()) {
				ApplicationUtil.displayErrors(errors);
				FacesContext.getCurrentInstance().validationFailed();
				setAssure(null);
			}else {
				// Assuré
				setAssure(assureService.findAssure(assMat, assCle));
				if(getAssure() == null ) {
					ApplicationUtil.displayError("assure_inexistant", null);
				}else {
					errors = carriereService.checkAssureCanDoRachat(assure);
					if (!errors.isEmpty()) {
						setAssure(null);
						ApplicationUtil.displayErrors(errors);
						FacesContext.getCurrentInstance().validationFailed();
					}else {
						//PrimeFaces.current().executeScript("console.log('ddd');document.getElementById('frm-rachat:date_demande_input').focus();;");
						//PrimeFaces.current().focus("frm-rachat:trimestre_debut_input");
						PrimeFaces.current().executeScript("setTimeout(function(){ document.getElementById('frm-rachat:date_demande_input').focus(); }, 100)");
					}
				}
			}
		}
	}	
	//-----
	public void refreshAgeDemande() {
		if(dateDemande != null) {
			setAgeDateDemande((short)Period.between(assure.getBenDtnais().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), dateDemande.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).getYears());
			setTauxRachat(carriereService.findTauxRachatByDateRef(ageDateDemande));			
		}else {
			setAgeDateDemande(null);
			setTauxRachat(null);
		}
		//refreshRachatData();
	}
	//-----
	public void refreshRachatData() {
		if(dateDemande!=null && trimestreDebut!=null && anneeDebut!=null && trimestreFin!=null && anneeFin!=null) {
			Map<String, String> errors = carriereService.checkRachat(assure, dateDemande,  trimestreDebut, anneeDebut, trimestreFin, anneeFin);
			if (!errors.isEmpty()) {
				setRachat(null);
				ApplicationUtil.displayErrors(errors);
				FacesContext.getCurrentInstance().validationFailed();
			}else {
				rachat = new RachatDto();			
				rachat.setAssure(assure);
				rachat.setDateDemande(dateDemande);
				rachat.setTauxRachat(tauxRachat);
				rachat.setTrimestreDebut(trimestreDebut);
				rachat.setAnneeDebut(anneeDebut);
				rachat.setTrimestreFin(trimestreFin);
				rachat.setAnneeFin(anneeFin);
				rachat.setNombreDeTrimestres( (short) -1);
				rachat = carriereService.processRachat(rachat);
			}
		}else {
			setRachat(null);
		}
	}
	//-----
	public void generateRachatOrdrePaiement() {
		try {
			carriereService.generateRachatOrdrePaiement(rachat, sessionBean.getUserConnectedAs(), sessionBean.getUserMatricule());
			ordrePaiemenentGenerated = true;
			PrimeFaces.current().executeScript("PF('dlgEstimation').hide()");
			ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
		}catch(Exception e) {
			ApplicationUtil.displayError("erreur_non_programmee","msg_dlg_estimation");
			e.printStackTrace();
		}
	}
	//-----
	/*
	public void performRachat() {
		Map<String, String> errors = carriereService.checkRachat(assure, dateDemande,  trimestreDebut, anneeDebut, trimestreFin, anneeFin);
		if (!errors.isEmpty()) {
			ApplicationUtil.displayErrors(errors);
			FacesContext.getCurrentInstance().isValidationFailed();
		}else {
			errors = carriereService.processRachat(
					assure, dateDemande,  trimestreDebut, 
					anneeDebut, trimestreFin, anneeFin, 
					tauxRachat, ageDateDemande
			);
			//System.out.println(salaireTrimRachat);
		};
	}
	*/
	//--------------------------------------------------------------------------------	

	
	
	//--------------------------------------------------------------------------------		
	public Integer getAssMat() {
		return assMat;
	}	
	public void setAssMat(Integer assMat) {
		this.assMat = assMat;
	}
	//-----
	public Short getAssCle() {
		return assCle;
	}
	public void setAssCle(Short assCle) {
		this.assCle = assCle;
	}
	//-----
	public AssureDto getAssure() {
		return assure;
	}	
	public void setAssure(AssureDto assure) {
		this.assure = assure;
	}
	//-----
	public Short getTrimestreDebut() {
		return trimestreDebut;
	}
	public void setTrimestreDebut(Short trimestreDebut) {
		this.trimestreDebut = trimestreDebut;
	}
	//-----
	public Short getAnneeDebut() {
		return anneeDebut;
	}
	public void setAnneeDebut(Short anneeDebut) {
		this.anneeDebut = anneeDebut;
	}
	//-----
	public Short getTrimestreFin() {
		return trimestreFin;
	}
	public void setTrimestreFin(Short trimestreFin) {
		this.trimestreFin = trimestreFin;
	}
	//-----
	public Short getAnneeFin() {
		return anneeFin;
	}
	public void setAnneeFin(Short anneeFin) {
		this.anneeFin = anneeFin;
	}
	//-----
	public Date getDateDemande() {
		return dateDemande;
	}
	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}
	//-----
	public Short getTauxRachat() {
		return tauxRachat;
	}
	public void setTauxRachat(Short tauxRachat) {
		this.tauxRachat = tauxRachat;
	}
	//-----
	public Short getAgeDateDemande() {
		return ageDateDemande;
	}
	public void setAgeDateDemande(Short ageDateDemande) {
		this.ageDateDemande = ageDateDemande;
	}
	//-----
	public RachatDto getRachat() {
		return rachat;
	}
	public void setRachat(RachatDto rachat) {
		this.rachat = rachat;
	}
	//-----
	public SessionBean getSessionBean() {
		return sessionBean;
	}
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	//-----
	public boolean isOrdrePaiemenentGenerated() {
		return ordrePaiemenentGenerated;
	}
	public void setOrdrePaiemenentGenerated(boolean ordrePaiemenentGenerated) {
		this.ordrePaiemenentGenerated = ordrePaiemenentGenerated;
	}
	//--------------------------------------------------------------------------------		
	
	

}
