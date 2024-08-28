package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
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
import tn.nat.cnss.reconstitutioncarriere.model.RcTransfertMatricule;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;
import tn.nat.cnss.reconstitutioncarriere.service.CarriereService;
import tn.nat.cnss.reconstitutioncarriere.util.ApplicationUtil;

@ManagedBean(name = "rcTransfertMatriculeController")
@ViewScoped
public class RcTransfertMatriculeController extends SpringBeanAutowiringSupport implements Serializable{
	
	private static final long serialVersionUID = 1325297861684281054L;
	
	private Integer assMatSource;
	private Short assCleSource;
	private AssureDto assureSource;
	private List<CarriereAssure> carriereAssureSource;
	
	private Integer assMatDestination;
	private Short assCleDestination;
	private AssureDto assureDestination;
	
	@Autowired
	private AssureService assureService;	
	@Autowired
	private CarriereService carriereService;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	private RcTransfertMatricule transfert;
	
	
	
	//--------------------------------------------------------------------------------
	public void refreshAssureSource() {
		if(getAssMatSource() != null && getAssCleSource() != null) {
			
			Map<String, String> errors = assureService.checkCleAssure(assMatSource, assCleSource);
			if(!errors.isEmpty()) {
				ApplicationUtil.displayErrors(errors);
				FacesContext.getCurrentInstance().validationFailed();
				setAssureSource(null);
				setCarriereAssureSource(null);
			}else {
				// Assuré
				setAssureSource(assureService.findAssure(assMatSource, assCleSource));
				if(getAssureSource() == null ) {
					ApplicationUtil.displayError("assure_inexistant", null);
					setCarriereAssureSource(null);
				}else{				
					refreshCarriereSource();
					if(assureDestination != null) {
						errors  = carriereService.checkTransfertMatricule(assureSource, assureDestination);
						if(!errors.isEmpty()) {
							ApplicationUtil.displayErrors(errors);
							FacesContext.getCurrentInstance().validationFailed();
						}
					}
				}
			}
		}else {
			setAssureSource(null);
			setCarriereAssureSource(null);
		}
	}	
	//-----
	public void refreshAssureDestination() {
		if(getAssMatDestination() != null && getAssCleDestination() != null) {
			
			Map<String, String> errors = assureService.checkCleAssure(assMatDestination, assCleDestination);
			if(!errors.isEmpty()) {
				ApplicationUtil.displayErrors(errors);
				FacesContext.getCurrentInstance().validationFailed();
				setAssureDestination(null);
			}else {
				// Assuré
				setAssureDestination(assureService.findAssure(assMatDestination, assCleDestination));
				if(getAssureDestination() != null ) {
					if (getAssureSource() != null) {
						errors  = carriereService.checkTransfertMatricule(assureSource, assureDestination);
						if(!errors.isEmpty()) {
							ApplicationUtil.displayErrors(errors);
							FacesContext.getCurrentInstance().validationFailed();
						}else {
							refreshCarriereSource();
							// Si une opération de transfert en attente d'exploitation existe => possibilité d'annulation						
							transfert = carriereService.findTransfertNonExploiteeByAssuresSourceDestination(assureSource, assureDestination);
							if (transfert != null) {
								if (transfert.getDateExploitation() != null) {
									ApplicationUtil.displayError("operation_exploitee", null);
								}
							}
						}
					}
				}else {				
					ApplicationUtil.displayError("assure_inexistant", null);
				}
			}
		}
	}	
	//-----	
	public void refreshCarriereSource() {
		setCarriereAssureSource(carriereService.loadCarriereByAssureMatriculeCle(assureSource.getAssMat(), assureSource.getAssCle()));
	}
	//-----
	public void processTransfertMatricule() {
		Map<String, String> errors  = carriereService.processTransfertMatricule(assureSource, assureDestination, sessionBean.getUserMatricule(), sessionBean.getUserConnectedAs());
		if(!errors.isEmpty()) {
			ApplicationUtil.displayErrors(errors);
			FacesContext.getCurrentInstance().validationFailed();
		}else {
			ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
		}
	}
	//-----
	public void processCancelTransfertMatricule() {
		carriereService.processCancelTransfertMatricule(transfert);		
		ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
	}
	
	
	//--------------------------------------------------------------------------------
	
	
	
	
	//--------------------------------------------------------------------------------
	public Integer getAssMatSource() {
		return assMatSource;
	}
	public void setAssMatSource(Integer assMatSource) {
		this.assMatSource = assMatSource;
	}
	//-----
	public Short getAssCleSource() {
		return assCleSource;
	}
	public void setAssCleSource(Short assCleSource) {
		this.assCleSource = assCleSource;
	}
	//-----
	public AssureDto getAssureSource() {
		return assureSource;
	}
	public void setAssureSource(AssureDto assureSource) {
		this.assureSource = assureSource;
	}
	//-----
	public List<CarriereAssure> getCarriereAssureSource() {
		return carriereAssureSource;
	}
	public void setCarriereAssureSource(List<CarriereAssure> carriereAssureSource) {
		this.carriereAssureSource = carriereAssureSource;
	}
	//-----
	public Integer getAssMatDestination() {
		return assMatDestination;
	}
	public void setAssMatDestination(Integer assMatDestination) {
		this.assMatDestination = assMatDestination;
	}
	//-----
	public Short getAssCleDestination() {
		return assCleDestination;
	}
	public void setAssCleDestination(Short assCleDestination) {
		this.assCleDestination = assCleDestination;
	}
	//-----
	public AssureDto getAssureDestination() {
		return assureDestination;
	}
	public void setAssureDestination(AssureDto assureDestination) {
		this.assureDestination = assureDestination;
	}
	//-----
	public SessionBean getSessionBean() {
		return sessionBean;
	}
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	//-----
	public RcTransfertMatricule getTransfert() {
		return transfert;
	}
	public void setTransfert(RcTransfertMatricule transfert) {
		this.transfert = transfert;
	}
	//--------------------------------------------------------------------------------
	

}
