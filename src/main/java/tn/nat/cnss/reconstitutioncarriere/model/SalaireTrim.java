package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "CARRIERE_ASSURE_OLD")
public class SalaireTrim implements Serializable{

	private static final long serialVersionUID = 2179094679001580318L;
	
	@EmbeddedId
	private SalaireTrimId id;
	@Column(name = "NUM_PAGE")
	private Short numPage;
	@Column(name = "SAlAIRE")
	private BigDecimal Salaire;
	@Column(name = "EMP_MAT")
	private Integer empMat;
	@Column(name = "EMP_CLE")
	private Short empCle;
	
	//----
	public SalaireTrimId getId() {
		return id;
	}
	public void setId(SalaireTrimId id) {
		this.id = id;
	}
	//----
	public Short getNumPage() {
		return numPage;
	}
	public void setNumPage(Short numPage) {
		this.numPage = numPage;
	}
	//----
	public BigDecimal getSalaire() {
		return Salaire;
	}
	public void setSalaire(BigDecimal salaire) {
		Salaire = salaire;
	}
	//----
	public Integer getEmpMat() {
		return empMat;
	}
	public void setEmpMat(Integer empMat) {
		this.empMat = empMat;
	}
	//----
	public Short getEmpCle() {
		return empCle;
	}
	public void setEmpCle(Short empCle) {
		this.empCle = empCle;
	}
	//----
	
	
	

}
