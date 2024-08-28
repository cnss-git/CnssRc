package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import tn.nat.cnss.reconstitutioncarriere.model.Employeur;
import tn.nat.cnss.reconstitutioncarriere.model.EmployeurId;
import tn.nat.cnss.reconstitutioncarriere.model.RcJugement;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.repository.EmployeurRepository;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;
import tn.nat.cnss.reconstitutioncarriere.service.CarriereService;
import tn.nat.cnss.reconstitutioncarriere.util.ApplicationUtil;

@ManagedBean(name = "rcJugementController")
@ViewScoped
public class RcJugementController extends SpringBeanAutowiringSupport implements Serializable{
	
	private static final long serialVersionUID = 272835559616385547L;
	
	
	private Integer assMat;
	private Short assCle;
	private AssureDto assure;
	private RcJugement toDelete;
	
	
	private Integer empMat;
	private Short empCle;
	private Employeur employeur;
	private List<RcJugement> jugementList;
	private Long totalSalaire;
	
	
	
	@Autowired
	private AssureService assureService;
	
	@Autowired
	private CarriereService carriereService;
	
	@Autowired
	private EmployeurRepository employeurRepository;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	

	//private static final Logger logger = LoggerFactory.getLogger(RcJugementController.class);
	
	
	//--------------------------------------------------------------------------------
	@PostConstruct
	public void init() {
	}
	//-----
	public void refreshAssure() {
		jugementList = new ArrayList<RcJugement>();
		if(getAssMat()!=null && getAssCle()!=null) {
			Map<String, String> errors = assureService.checkCleAssure(assMat, assCle);
			if(!errors.isEmpty()) {
				ApplicationUtil.displayErrors(errors);
				FacesContext.getCurrentInstance().validationFailed();
				setAssure(null);
			}else {
				// Assuré
				setAssure(assureService.findAssure(assMat, assCle));
				if(getAssure() != null ) {			
					refreshJugementList();
				}else {
					setEmpMat(null);
					setEmpCle(null);
					setEmployeur(null);
					ApplicationUtil.displayError("assure_inexistant", null);
				}
			}
		}else {
			setAssure(null);
		}
	}
	//-----
	public void refreshEmployeur() {
		if(getEmpMat()!=null && getEmpCle()!=null) {
			setEmployeur(employeurRepository.findById(new EmployeurId(empMat, empCle)).orElse(null));
			if (getEmployeur() != null) {
				if (assure.getRegCod()!=800 && employeur.getRegime().compareTo(assure.getRegCod()) != 0) {
					setJugementList(new ArrayList<RcJugement>());
					ApplicationUtil.displayError("regimes_employeur_assure_non_conformes", null);	
					setEmployeur(null);
					FacesContext.getCurrentInstance().validationFailed();
				}else {
					refreshJugementList();
				}
			}else {
				setJugementList(new ArrayList<RcJugement>());
				ApplicationUtil.displayError("employeur_inexistant", null);	
				FacesContext.getCurrentInstance().validationFailed();
			}
		}else {
			setEmployeur(null);
			refreshJugementList();
		}
	}
	//-----
	public void refreshJugementList() {
		jugementList = new ArrayList<RcJugement>();
		if(getAssure() != null ) {
			jugementList = carriereService.loadListJugementByAssure(assMat, assCle);
			if (getEmployeur() != null) {
				jugementList = jugementList.stream().filter(s -> s.getEmployeur().getId().getEmpMat().compareTo(empMat) == 0 && s.getEmployeur().getId().getEmpCle().compareTo(empCle)==0).collect(Collectors.toList());
			}
		}
		totalSalaire = jugementList.stream().mapToLong(s -> s.getSalaire()).sum();
	}
	//-----
	public void onRowEdit(RowEditEvent event) {
		RcJugement jugement = (RcJugement)event.getObject();
		jugement.setAssMat(assMat);
		jugement.setAssCle(assCle);
		jugement.setEmployeur(employeur);
		jugement.setAgent(Integer.parseInt(sessionBean.getUserMatricule()));
		jugement.setBrSaisie(sessionBean.getUserConnectedAs().getBurCod());
		Map<String, String> errors = carriereService.processJugement(jugement);
		if(!errors.isEmpty()) {
			ApplicationUtil.displayErrors(errors);
			FacesContext.getCurrentInstance().validationFailed();
		}else {
			refreshJugementList();
			ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_INFO, "information", "done");
		}
    }
	//-----
    public void onRowCancel(RowEditEvent event) {
    	ApplicationUtil.displayMessage("msgs", FacesMessage.SEVERITY_WARN, "warning", "updateCanceled");
    }
    //-----
    public void initNewJugement() {
    	RcJugement jugement = new RcJugement();
    	getJugementList().add(jugement);
    }
    //-----
    public void prepareDelete(RcJugement salaireJugement) {
    	this.toDelete = salaireJugement;
    }
    //-----
    public void processDelete() {
    	carriereService.processDeleteJugement(toDelete);
    	refreshJugementList();
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
	public List<RcJugement> getJugementList() {
		return jugementList;
	}
	public void setJugementList(List<RcJugement> jugementList) {
		this.jugementList = jugementList;
	}
	//---
	public Long getTotalSalaire() {
		return totalSalaire;
	}
	public void setTotalSalaire(Long totalSalaire) {
		this.totalSalaire = totalSalaire;
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
