package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class EmployeurId implements Serializable{

	private static final long serialVersionUID = 6332797800154175919L;
	
	
	@Column(name = "EMP_MAT")
	private Integer empMat;
	
	@Column(name = "EMP_CLE")
	private Short empCle;
	
	
	
	

	public EmployeurId() {
		super();
	}


	public EmployeurId(Integer empMat, Short empCle) {
		this.empMat = empMat;
		this.empCle = empCle;
	}
	
	
	public Integer getEmpMat() {
		return empMat;
	}
	public void setEmpMat(Integer empMat) {
		this.empMat = empMat;
	}
	

	public Short getEmpCle() {
		return empCle;
	}
	public void setEmpCle(Short empCle) {
		this.empCle = empCle;
	}
	

}
