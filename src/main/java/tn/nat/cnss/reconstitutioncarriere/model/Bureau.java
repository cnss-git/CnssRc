package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BUREAU")
public class Bureau implements Serializable{

	private static final long serialVersionUID = 6232939677995333238L;
	
	@Id
	@Column(name = "BUR_COD")
	private Short burCod;
	
	@Column(name = "BUR_INTIT")
	private String burIntitFr;
	
	@Column(name = "BUR_INTIT_AR")
	private String burInitAr;
	
	

	public Short getBurCod() {
		return burCod;
	}
	public void setBurCod(Short burCod) {
		this.burCod = burCod;
	}
	

	public String getBurIntitFr() {
		return burIntitFr;
	}
	public void setBurIntitFr(String burIntitFr) {
		this.burIntitFr = burIntitFr;
	}
	

	public String getBurInitAr() {
		return burInitAr;
	}
	public void setBurInitAr(String burInitAr) {
		this.burInitAr = burInitAr;
	}
	

}
