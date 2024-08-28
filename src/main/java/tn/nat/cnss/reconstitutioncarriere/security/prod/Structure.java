package tn.nat.cnss.reconstitutioncarriere.security.prod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Bureau")
public class Structure implements Serializable{

	private static final long serialVersionUID = -6262881395045770115L;
	
	
	private Short burCod;
	private Byte  burTypbur;
	private	String burIntit;
	private String burIntitAr;
	
	
	@Id
	@Column(name = "BUR_COD")
	public Short getBurCod() {
		return burCod;
	}
	public void setBurCod(Short burCod) {
		this.burCod = burCod;
	}
	
	@Column(name = "BUR_TYPBUR")
	public Byte getBurTypbur() {
		return burTypbur;
	}
	public void setBurTypbur(Byte burTypbur) {
		this.burTypbur = burTypbur;
	}
	
	@Column(name = "BUR_INTIT")
	public String getBurIntit() {
		return burIntit;
	}
	public void setBurIntit(String burIntit) {
		this.burIntit = burIntit;
	}
	
	@Column(name = "BUR_INTIT_AR")
	public String getBurIntitAr() {
		return burIntitAr;
	}
	public void setBurIntitAr(String burIntitAr) {
		this.burIntitAr = burIntitAr;
	}
	

	
}
