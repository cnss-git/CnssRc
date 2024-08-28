package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import tn.nat.cnss.reconstitutioncarriere.model.Employeur;
import tn.nat.cnss.reconstitutioncarriere.repository.EmployeurRepository;
import tn.nat.cnss.reconstitutioncarriere.service.impl.EmployeurlazyDataModel;

@ManagedBean(name = "controller")
@ViewScoped
public class ZLazyDtLoadingController extends SpringBeanAutowiringSupport implements Serializable{
	
	private static final long serialVersionUID = -9195868674675169752L;
	
	

	//-----------------------------------------------------------
	@Autowired
	private EmployeurRepository repository;

	private LazyDataModel<Employeur> model;
	private String filtreRaisonSociale;
	private Integer filtreMatricule;
	private Map<String, Object> filters = new HashMap<String, Object>();
	//-----------------------------------------------------------
	
	
	
	
	
	//-----------------------------------------------------------
	@PostConstruct
	public void init() {
		model = new EmployeurlazyDataModel(repository, filters);
	}
	
	
	public void refresh() {
		//System.out.println(filtreRaisonSociale);
		filters.put("pEmpRais", filtreRaisonSociale);
		filters.put("pMatricule", filtreMatricule);
		model = new EmployeurlazyDataModel(repository, filters);
	}
	//-----------------------------------------------------------
	
	
	
	


	//-----------------------------------------------------------
	public LazyDataModel<Employeur> getModel() {
		return model;
	}
	public void setModel(LazyDataModel<Employeur> model) {
		this.model = model;
	}

	public String getFiltreRaisonSociale() {
		return filtreRaisonSociale;
	}
	public void setFiltreRaisonSociale(String filtreRaisonSociale) {
		this.filtreRaisonSociale = filtreRaisonSociale;
	}

	public Map<String, Object> getFilters() {
		return filters;
	}
	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public Integer getFiltreMatricule() {
		return filtreMatricule;
	}
	public void setFiltreMatricule(Integer filtreMatricule) {
		this.filtreMatricule = filtreMatricule;
	}
	//-----------------------------------------------------------
	

}
