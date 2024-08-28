package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.IsoFields;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import tn.nat.cnss.reconstitutioncarriere.model.Employeur;
import tn.nat.cnss.reconstitutioncarriere.model.EmployeurId;
import tn.nat.cnss.reconstitutioncarriere.model.RcSalaireEstimatif;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.repository.EmployeurRepository;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;
import tn.nat.cnss.reconstitutioncarriere.service.CarriereService;
import tn.nat.cnss.reconstitutioncarriere.util.ApplicationUtil;

@ManagedBean(name= "rcSalaireEstimatifController")
@ViewScoped
public class RcSalaireEstimatifDemandeRetraiteController extends SpringBeanAutowiringSupport implements Serializable{
	
	private static final long serialVersionUID = -3084924089104423192L;
	
	
	private Integer assMat;
	private Short assCle;
	private AssureDto assure;
	//private Short trimestreEncours;
	//private Short anneeEnCours;
	private Integer empMat;
	private Short empCle;
	private Employeur employeur;
	private RcSalaireEstimatif rcSalaireEstimatif;
	private boolean modeViewOnly = false;
	
	@Autowired
	private AssureService assureService;
	
	@Autowired
	private EmployeurRepository employeurRepository; 
	
	@Autowired
	private CarriereService carriereService;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	
	
	
	//--------------------------------------------------------------------------------
	//-----
	public void refreshAssure() {
		PrimeFaces.current().resetInputs("frm-salaire_estimatif:p_salaire_estimatif");	
		setEmpMat(null);
		setEmpCle(null);
		setEmployeur(null);
		setModeViewOnly(false);
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
					rcSalaireEstimatif = carriereService.findSalaireEstimatifByAssure(assMat, assCle);
					if (rcSalaireEstimatif == null) {
						rcSalaireEstimatif = new RcSalaireEstimatif();
						rcSalaireEstimatif.setTrimestre((short) LocalDate.now().get(IsoFields.QUARTER_OF_YEAR));
						rcSalaireEstimatif.setAnnee((short) (Year.now().getValue()));
						if(
								!carriereService.checkAssureSalarie(assure).isEmpty()
								||
								!carriereService.checkAssureActifTrimestre(assure,rcSalaireEstimatif.getTrimestre(),rcSalaireEstimatif.getAnnee()).isEmpty()
						) {
							ApplicationUtil.displayErrors(carriereService.checkAssureSalarie(assure));
							ApplicationUtil.displayErrors(carriereService.checkAssureActifTrimestre(assure,rcSalaireEstimatif.getTrimestre(),rcSalaireEstimatif.getAnnee()));
							FacesContext.getCurrentInstance().validationFailed();
							setAssure(null);
						}/*else {
							PrimeFaces.current().executeScript("setTimeout(function(){ document.getElementById('frm-salaire_estimatif:emp_mat_input').value = '1';document.getElementById('frm-salaire_estimatif:emp_mat_input').focus();document.getElementById('frm-salaire_estimatif:emp_mat_input').value = '2'; }, 500);");
						}*/
					}else{
						setEmpMat(rcSalaireEstimatif.getEmployeur().getId().getEmpMat());
						setEmpCle(rcSalaireEstimatif.getEmployeur().getId().getEmpCle());
						setEmployeur(rcSalaireEstimatif.getEmployeur());
						/*
						if (rcSalaireEstimatif.getDateExploitation() != null) {
							setModeViewOnly(true);
							ApplicationUtil.displayError("operation_exploitee");
						}
						*/
						//PrimeFaces.current().executeScript("setTimeout(function(){ document.getElementById('frm-salaire_estimatif:emp_mat_input').focus(); }, 100)");
					}
				}else {
					ApplicationUtil.displayError("assure_inexistant", null);
				}
			}
		}else {
			setAssure(null);
		}
	}	
	//-----
	
	
	//-----
	public void refreshEmployeur() {
		if(getEmpMat() != null && getEmpCle() != null) {
			// Employeur
			setEmployeur(employeurRepository.findById(new EmployeurId(getEmpMat(), getEmpCle())).orElse(null));
			if(getEmployeur() != null ) {			
				//TODO
			}else {
				ApplicationUtil.displayError("employeur_inexistant", null);
				FacesContext.getCurrentInstance().validationFailed();
			}
		}else {
			setEmployeur(null);
		}
	}
	//-----
	
	
	//-----
	public void save() {
		
		rcSalaireEstimatif.setEmployeur(employeur);
		rcSalaireEstimatif.setAssMat(assMat);
		rcSalaireEstimatif.setAssCle(assCle);
		rcSalaireEstimatif.setMatriculeAgent(Integer.parseInt(sessionBean.getUserMatricule()));
		rcSalaireEstimatif.setBrSaisie(sessionBean.getUserConnectedAs().getBurCod());
		rcSalaireEstimatif.setDateExploitation(null);
		
		Map<String, String> errors =  carriereService.processSalaireEstimatif(rcSalaireEstimatif);
		if(!errors.isEmpty()) {
			ApplicationUtil.displayErrors(errors);
			FacesContext.getCurrentInstance().validationFailed();
		}else {
			setModeViewOnly(true);
			ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
		}
	}
	//-----
	
	
	//-----
	public void delete() {
		carriereService.processDeleteSalaireEstimatif(rcSalaireEstimatif);
		refreshAssure();
		setEmpMat(null);
		setEmpCle(null);
		refreshEmployeur();
		ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
	}
	//-----
	//--------------------------------------------------------------------------------



	
	
	
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
	public AssureDto getAssure() {
		return assure;
	}
	public void setAssure(AssureDto assure) {
		this.assure = assure;
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
	public RcSalaireEstimatif getRcSalaireEstimatif() {
		return rcSalaireEstimatif;
	}
	public void setRcSalaireEstimatif(RcSalaireEstimatif rcSalaireEstimatif) {
		this.rcSalaireEstimatif = rcSalaireEstimatif;
	}
	//---
	public boolean isModeViewOnly() {
		return modeViewOnly;
	}
	public void setModeViewOnly(boolean modeViewOnly) {
		this.modeViewOnly = modeViewOnly;
	}
	//---
	public SessionBean getSessionBean() {
		return sessionBean;
	}
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	//--------------------------------------------------------------------------------
	
	

}
