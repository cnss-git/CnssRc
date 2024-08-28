package tn.nat.cnss.reconstitutioncarriere.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import tn.nat.cnss.reconstitutioncarriere.model.RcDemandeDgObjet;
import tn.nat.cnss.reconstitutioncarriere.repository.RcDemandeDgObjetRepository;

@FacesConverter("objetDemandeDgConverter")
public class RcObjetDemandeDgConverter extends SpringBeanAutowiringSupport implements Converter, Serializable{

	private static final long serialVersionUID = 8815069024368619715L;
	
	
	@Autowired
	RcDemandeDgObjetRepository  rcDemandeDgObjetRepository;
	
	

	@Override
	public RcDemandeDgObjet getAsObject(FacesContext context, UIComponent component, String value) {
		if (value==null || value=="") {
			return null;
		}
		try {
			return rcDemandeDgObjetRepository.findById(Short.valueOf(value)).orElse(null);
		}catch(NumberFormatException e) {
			return null;
		}		
	}
	
	
	

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (! (value instanceof RcDemandeDgObjet)) {
			return null;
		}
		
		RcDemandeDgObjet rcDemandeDgObjet = (RcDemandeDgObjet) value;
		return value != null ? rcDemandeDgObjet.getCode().toString() : null;
		
	}
	

}
