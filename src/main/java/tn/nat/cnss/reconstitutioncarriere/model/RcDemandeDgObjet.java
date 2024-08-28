package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RC_DEMANDE_DG_OBJET")
public class RcDemandeDgObjet implements Serializable{

	private static final long serialVersionUID = -8449314134686145980L;
	
	
	@Id
	@Column(name = "OBJET_CODE")
	private Short code;
	
	@Column(name = "OBJET_LIBELLE_FR")
	private String libelleFr;
	
	@Column(name = "OBJET_LIBELLE_AR")
	private String libelleAr;

	public Short getCode() {
		return code;
	}
	public void setCode(Short code) {
		this.code = code;
	}
	

	public String getLibelleFr() {
		return libelleFr;
	}
	public void setLibelleFr(String libelleFr) {
		this.libelleFr = libelleFr;
	}
	

	public String getLibelleAr() {
		return libelleAr;
	}
	public void setLibelleAr(String libelleAr) {
		this.libelleAr = libelleAr;
	}
	
	
	
	
	
	@Override
	public boolean equals(Object other) {
		if((other == null) || (getClass() != other.getClass())){
	        return false;
	    }
		RcDemandeDgObjet otherRcDemandeDgObjet = (RcDemandeDgObjet)other;
	    return getCode().compareTo(otherRcDemandeDgObjet.getCode())==0;
	}
	
	

}
