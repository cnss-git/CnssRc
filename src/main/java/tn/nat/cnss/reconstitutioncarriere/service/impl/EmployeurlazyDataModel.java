package tn.nat.cnss.reconstitutioncarriere.service.impl;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import tn.nat.cnss.reconstitutioncarriere.model.Employeur;
import tn.nat.cnss.reconstitutioncarriere.repository.EmployeurRepository;


public class EmployeurlazyDataModel extends LazyDataModel<Employeur>{

	private static final long serialVersionUID = -7121163996280193958L;
	
	private EmployeurRepository repository;
	private Map<String, Object> filters;
	
	
	public EmployeurlazyDataModel(EmployeurRepository repository, Map<String, Object> filters) {
		this.repository = repository;
		this.filters = filters;
	}
	
	
	


	@Override
	public List<Employeur> load(int first, int pageSize, String sortField, SortOrder sortOrder,	Map<String, Object> filters) {
		
		int pageNumber = first/pageSize;
		
		String pEmpRais = null;
		if(this.filters.get("pEmpRais") != null) {
			pEmpRais = (String) this.filters.get("pEmpRais");
		}
		Integer pMatricule = null;
		if (this.filters.get("pMatricule") != null) {
			pMatricule = (Integer) this.filters.get("pMatricule");
		}
		
		
		Page<Employeur> result = repository.find(pEmpRais, pMatricule, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id.empMat")));
		
		setRowCount(  (int)  repository.count(pEmpRais, pMatricule));
		
		return result.getContent();
	}
	
	
	

}
