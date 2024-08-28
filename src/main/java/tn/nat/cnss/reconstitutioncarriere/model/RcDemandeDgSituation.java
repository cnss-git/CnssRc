package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RC_DEMANDE_DG_SITUATION")
public class RcDemandeDgSituation implements Serializable{

	private static final long serialVersionUID = -424875394824505677L;
	
	
	@Id
	@Column(name = "SITUATION_CODE")
	private Short code;
	
	@Column(name = "SITUATION_LIBELLE_FR")
	private String libelleFr;
	
	@Column(name = "SITUATION_LIBELLE_AR")
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
	

}
