package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TYPE_SALAIRE")
public class TypeSalaire implements Serializable{

	private static final long serialVersionUID = -3703813942451249446L;
	
	
	@Id
	@Column(name = "CODE_TYPE_SALAIRE")
	private Short codeTypeSalaire;
	
	@Column(name = "LIBELLE_FR")
	private String libelleFr;
	
	@Column(name = "LIBELLE_AR")
	private String libelleAr;
	
	
	
	

	public Short getCodeTypeSalaire() {
		return codeTypeSalaire;
	}
	public void setCodeTypeSalaire(Short codeTypeSalaire) {
		this.codeTypeSalaire = codeTypeSalaire;
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
		TypeSalaire otherTypeSalaire = (TypeSalaire)other;
	    return getCodeTypeSalaire().compareTo(otherTypeSalaire.getCodeTypeSalaire())==0;
	}
	

}
