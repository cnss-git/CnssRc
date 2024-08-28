package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDg;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgHistoriquSituation;
import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgObjet;
import tn.nat.cnss.reconstitutioncarriere.model.dto.AssureDto;
import tn.nat.cnss.reconstitutioncarriere.model.dto.RcDemandeDgDto;
import tn.nat.cnss.reconstitutioncarriere.service.AssureService;
import tn.nat.cnss.reconstitutioncarriere.service.CarriereService;
import tn.nat.cnss.reconstitutioncarriere.util.ApplicationUtil;

@ManagedBean(name = "rcDgListeDemandesController")
@ViewScoped
public class RcDgListeDemandesController extends SpringBeanAutowiringSupport implements Serializable{

	private static final long serialVersionUID = -1425219469700692730L;
	
	@Autowired
	private CarriereService carriereService;
	
	@Autowired
	private AssureService assureService;
	
	//private List<RcDemandeDg> listeDemandes;
	private List<RcDemandeDgDto> listDemandes;
	//private List<RcDemandeDg> filteredListDemandes;
	private List<RcDemandeDgDto> filteredListDemandes;
	private List<RcDemandeDgObjet> listObjetsDemandes;
	private RcDemandeDg demandeDg;	
	private RcDemandeDgObjet demandeDgObjet;
	private Integer demandeDgAssMat;
	private Short demandeDgAssCle;
	private AssureDto demandeAssure;
	private List<RcDemandeDgHistoriquSituation> selectedDemandeHistorique;
	
	private Integer filterAssMat;	
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	
	//---------------------------------------------------------------------------------------------------------------
	@PostConstruct
	public void init() {
		//listeDemandes = carriereService.findAllDemandesDgByStructure(sessionBean.getUserConnectedAs());
		listDemandes = carriereService.findAllDemandesByStructure(sessionBean.getUserConnectedAs());
		filterListDemandes();
		listObjetsDemandes = carriereService.findAllObjetsDemandes();
	}
	//---
	public void initAddEditDialog() {
		demandeDg = new RcDemandeDg();
		setDemandeAssure(null);
		setDemandeDgObjet(null);
		setDemandeDgAssMat(null);
		setDemandeDgAssCle(null);
		PrimeFaces.current().resetInputs("frm-liste-demandes-dg:panel-add-edit-demande");
	}
	//---
	public void refreshDemandeAssure() {
		if (getDemandeDgAssMat()!=null && getDemandeDgAssCle()!=null) {
			setDemandeAssure(assureService.findAssure(demandeDgAssMat, demandeDgAssCle));
		}else {
			setDemandeAssure(null);
		}
	}
	//---
	public void saveDemande() {
		if (getDemandeAssure()==null) {
			ApplicationUtil.displayError("assure_inexistant","frm-liste-demandes-dg:msg-add-edit-demande");
		}else {
			try {
				demandeDg.setAssure(assureService.findMappedAssure(demandeDgAssMat, demandeDgAssCle));
				demandeDg.setObjetDemande(demandeDgObjet);
				Map<String, String> errors = carriereService.addDemande(demandeDg, sessionBean.getUserConnectedAs(), Integer.parseInt(sessionBean.getUserMatricule()));
				if (!errors.isEmpty()) {
					ApplicationUtil.displayErrors(errors);
					FacesContext.getCurrentInstance().validationFailed();
					return;
				}
				//listeDemandes = carriereService.findAllDemandesDgByStructure(sessionBean.getUserConnectedAs());
				listDemandes = carriereService.findAllDemandesByStructure(sessionBean.getUserConnectedAs());
				filterListDemandes();
				PrimeFaces.current().executeScript("PF('wAddEditDlgDemande').hide();");
			}catch(Exception e) {
				ApplicationUtil.displayError("erreur_non_programmee","frm-liste-demandes-dg:msg-add-edit-demande");
			}
		}
	}
	//---
	public void deleteDemande(RcDemandeDg demande) {
		carriereService.deleleDemande(demande);
		//listeDemandes = carriereService.findAllDemandesDgByStructure(sessionBean.getUserConnectedAs());
		listDemandes = carriereService.findAllDemandesByStructure(sessionBean.getUserConnectedAs());
		filterListDemandes();
	}
	//---
	public void historiqueDemande(RcDemandeDgDto demandeDto) {
		selectedDemandeHistorique = carriereService.findHistoriqueDemande(demandeDto);
	}
	//---
	public String toDg() {
		return "dg";
	}
	//---
	public void filterListDemandes() {
		/*
		filteredListDemandes = listeDemandes;
		if (filterAssMat != null) {
			filteredListDemandes = filteredListDemandes.stream().filter(d -> d.getAssure().getId().getAssMat().compareTo(filterAssMat)==0).collect(Collectors.toList());
		}
		*/
		filteredListDemandes = listDemandes;
		if (filterAssMat != null) {
			filteredListDemandes = filteredListDemandes.stream().filter(d -> d.getAssMat().compareTo(filterAssMat)==0).collect(Collectors.toList());
		}
	}
	//---------------------------------------------------------------------------------------------------------------



	
	//---------------------------------------------------------------------------------------------------------------
	/*
	public List<RcDemandeDg> getListeDemandes() {
		return listeDemandes;
	}
	public void setListeDemandes(List<RcDemandeDg> listeDemandes) {
		this.listeDemandes = listeDemandes;
	}	
	*/
	//---
	public List<RcDemandeDgObjet> getListObjetsDemandes() {
		return listObjetsDemandes;
	}
	public void setListObjetsDemandes(List<RcDemandeDgObjet> listObjetsDemandes) {
		this.listObjetsDemandes = listObjetsDemandes;
	}
	//---
	public RcDemandeDg getDemandeDg() {
		return demandeDg;
	}
	public void setDemandeDg(RcDemandeDg demandeDg) {
		this.demandeDg = demandeDg;
	}
	//---
	public RcDemandeDgObjet getDemandeDgObjet() {
		return demandeDgObjet;
	}
	public void setDemandeDgObjet(RcDemandeDgObjet demandeDgObjet) {
		this.demandeDgObjet = demandeDgObjet;
	}
	//---
	public Integer getDemandeDgAssMat() {
		return demandeDgAssMat;
	}
	public void setDemandeDgAssMat(Integer demandeDgAssMat) {
		this.demandeDgAssMat = demandeDgAssMat;
	}
	//---
	public Short getDemandeDgAssCle() {
		return demandeDgAssCle;
	}
	public void setDemandeDgAssCle(Short demandeDgAssCle) {
		this.demandeDgAssCle = demandeDgAssCle;
	}
	//---
	public AssureDto getDemandeAssure() {
		return demandeAssure;
	}
	public void setDemandeAssure(AssureDto demandeAssure) {
		this.demandeAssure = demandeAssure;
	}
	//---
	public SessionBean getSessionBean() {
		return sessionBean;
	}
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	//---
	public List<RcDemandeDgHistoriquSituation> getSelectedDemandeHistorique() {
		return selectedDemandeHistorique;
	}
	public void setSelectedDemandeHistorique(List<RcDemandeDgHistoriquSituation> selectedDemandeHistorique) {
		this.selectedDemandeHistorique = selectedDemandeHistorique;
	}
	//---
	public Integer getFilterAssMat() {
		return filterAssMat;
	}
	public void setFilterAssMat(Integer filterAssMat) {
		this.filterAssMat = filterAssMat;
	}
	//---
	/*
	public List<RcDemandeDg> getFilteredListDemandes() {
		return filteredListDemandes;
	}
	public void setFilteredListDemandes(List<RcDemandeDg> filteredListDemandes) {
		this.filteredListDemandes = filteredListDemandes;
	}
	*/
	//---
	public List<RcDemandeDgDto> getListDemandes() {
		return listDemandes;
	}
	public void setListDemandes(List<RcDemandeDgDto> listDemandes) {
		this.listDemandes = listDemandes;
	}
	//---
	public List<RcDemandeDgDto> getFilteredListDemandes() {
		return filteredListDemandes;
	}
	public void setFilteredListDemandes(List<RcDemandeDgDto> filteredListDemandes) {
		this.filteredListDemandes = filteredListDemandes;
	}
	//---------------------------------------------------------------------------------------------------------------
	
	
	
	

}
