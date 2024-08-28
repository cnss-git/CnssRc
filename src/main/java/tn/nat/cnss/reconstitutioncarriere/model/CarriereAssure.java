package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CARRIERE_ASSURE_OLD")
public class CarriereAssure implements Serializable{

	private static final long serialVersionUID = -8640377304247719590L;
	
	
	@EmbeddedId
	private CarriereAssureId id;
	
	@Column(name = "SALAIRE")
	private Long salaire;
	
	@Column(name = "EMP_MAT")
	private Integer empMat;
	
	@Column(name = "EMP_CLE")
	private Short empCle;
	
	@Column(name = "DATE_SAISIE")
	private Date dateSaisie;
	
	@Column(name = "CODE_VALIDATION")
	private String codeValidation;
	
	@Transient
	private boolean transfertCnrps;
	
	@Transient
	private Date dateExploitation;


	public CarriereAssureId getId() {
		return id;
	}
	public void setId(CarriereAssureId id) {
		this.id = id;
	}
	
	
	public Long getSalaire() {
		return salaire;
	}
	public void setSalaire(Long salaire) {
		this.salaire = salaire;
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
	
	
	public Date getDateSaisie() {
		return dateSaisie;
	}
	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}	
	
	
	public String getCodeValidation() {
		return codeValidation;
	}
	public void setCodeValidation(String codeValidation) {
		this.codeValidation = codeValidation;
	}
	
	
	public boolean getTransfertCnrps() {
		return transfertCnrps;
	}
	public void setTransfertCnrps(boolean transfertCnrps) {
		this.transfertCnrps = transfertCnrps;
	}
	
	
	public Date getDateExploitation() {
		return dateExploitation;
	}
	public void setDateExploitation(Date dateExploitation) {
		this.dateExploitation = dateExploitation;
	}
	
	
	
	
	
	
	
	

}
