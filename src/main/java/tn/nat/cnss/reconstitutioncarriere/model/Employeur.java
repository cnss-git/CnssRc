package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "EMPLOYEUR")
public class Employeur implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	private EmployeurId id;
	
	@Column(name="EMP_RAIS")
	private String empRais;	
	
	@Column(name = "EMP_RAIS_AR")
	private String empRaisAr;
	
	@Column(name = "REG_COD")
	private Short regime;
	
	@Column(name = "EMP_DTEFF")
	private Date empDteff;
	
	
	

	public EmployeurId getId() {
		return id;
	}
	public void setId(EmployeurId id) {
		this.id = id;
	}
	

	public String getEmpRais() {
		return empRais;
	}
	public void setEmpRais(String empRais) {
		this.empRais = empRais;
	}
	

	public String getEmpRaisAr() {
		empRaisAr = empRaisAr != null ? empRaisAr : empRais;
		return empRaisAr;
	}
	public void setEmpRaisAr(String empRaisAr) {
		this.empRaisAr = empRaisAr;
	}
	
	
	public Short getRegime() {
		return regime;
	}
	public void setRegime(Short regime) {
		this.regime = regime;
	}
	
	
	public Date getEmpDteff() {
		return empDteff;
	}
	public void setEmpDteff(Date empDteff) {
		this.empDteff = empDteff;
	}
	
	
}
