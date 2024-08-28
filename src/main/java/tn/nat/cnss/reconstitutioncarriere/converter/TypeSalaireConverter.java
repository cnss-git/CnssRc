package tn.nat.cnss.reconstitutioncarriere.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import tn.nat.cnss.reconstitutioncarriere.model.TypeSalaire;
import tn.nat.cnss.reconstitutioncarriere.repository.TypeSalaireRepository;

@FacesConverter("typeSalaireConverter")
public class TypeSalaireConverter extends SpringBeanAutowiringSupport implements Converter, Serializable{

	private static final long serialVersionUID = -2332364111321656177L;
	
	@Autowired
	TypeSalaireRepository  repository;

	@Override
	public TypeSalaire getAsObject(FacesContext context, UIComponent component, String value) {
		if (value==null || value=="") {
			return null;
		}
		try {
			return repository.findById(Short.parseShort(value)).orElse(null);
		}catch(NumberFormatException e) {
			return null;
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (! (value instanceof TypeSalaire)) {
			return null;
		}
		
		TypeSalaire typeSalaire = (TypeSalaire) value;
		return value != null ? typeSalaire.getCodeTypeSalaire().toString() : null;
		
	}
	
	
	
	
	

}
