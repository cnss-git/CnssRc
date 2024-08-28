package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import tn.nat.cnss.reconstitutioncarriere.model.CarriereAssure;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;
import tn.nat.cnss.reconstitutioncarriere.service.CarriereService;
import tn.nat.cnss.reconstitutioncarriere.util.ApplicationUtil;

@ManagedBean(name = "rcTransfertCnrpsController")
@ViewScoped
public class RcTransfertCnrpsController extends SpringBeanAutowiringSupport implements Serializable{
	
	private static final long serialVersionUID = 212715810964461321L;
	
	
	private Integer assMat;
	private Short assCle;
	private AssureDto assure;
	private Short trimestreDepart;
	private Short anneeDepart;
	private Short trimestreFin;
	private Short anneeFin;
	private List<CarriereAssure> listSalairesPourTransfert = new ArrayList<CarriereAssure>();
	private boolean modeViewOnly = false;
	
	@Autowired 
	private AssureService assureService;
	@Autowired
	private CarriereService carriereService;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	
	//--------------------------------------------------------------------------------
	public void refreshAssure() {
		//PrimeFaces.current().resetInputs("frm-salaire_estimatif:p_salaire_estimatif");
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
				}
			}
		}else {
			setAssure(null);
		}
		refreshCarriere();
	}	
	//-----
	public void refreshCarriere() {
		if(getAssure()!=null && getTrimestreDepart()!=null && getAnneeDepart()!=null && getTrimestreFin()!=null && getAnneeFin()!=null) {
			listSalairesPourTransfert =  carriereService.loadCarriereByAssureMatriculeCleTrimDepartTrimFin(assMat, assCle, trimestreDepart, anneeDepart, trimestreFin, anneeFin);
		}else {
			listSalairesPourTransfert = new ArrayList<CarriereAssure>();
		}
	}
	//-----
	public void refreshTransfert(CarriereAssure c) {
		//System.out.println(c.getId().getTrimestre()+" - "+c.getId().getAnnee()+"  "+c.getTransfertCnrps());
	}
	//-----
	public void save() {
		carriereService.processTransfertCnrps(listSalairesPourTransfert, sessionBean.getUserMatricule(), sessionBean.getUserConnectedAs());	
		ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
	}
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
	public Short getTrimestreDepart() {
		return trimestreDepart;
	}
	public void setTrimestreDepart(Short trimestreDepart) {
		this.trimestreDepart = trimestreDepart;
	}
	//---
	public Short getAnneeDepart() {
		return anneeDepart;
	}
	public void setAnneeDepart(Short anneeDepart) {
		this.anneeDepart = anneeDepart;
	}
	//---
	public Short getTrimestreFin() {
		return trimestreFin;
	}
	public void setTrimestreFin(Short trimestreFin) {
		this.trimestreFin = trimestreFin;
	}
	//---
	public Short getAnneeFin() {
		return anneeFin;
	}
	public void setAnneeFin(Short anneeFin) {
		this.anneeFin = anneeFin;
	}
	//---	
	public List<CarriereAssure> getListSalairesPourTransfert() {
		return listSalairesPourTransfert;
	}
	public void setListSalairesPourTransfert(List<CarriereAssure> listSalairesPourTransfert) {
		this.listSalairesPourTransfert = listSalairesPourTransfert;
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
