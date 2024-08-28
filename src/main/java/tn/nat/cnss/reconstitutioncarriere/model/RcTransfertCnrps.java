package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "RC_TRANSFERT_CNRPS")
public class RcTransfertCnrps implements Serializable{

	private static final long serialVersionUID = 8163172982473330766L;
	
	
	@Id
	@Column(name = "RCTR_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_RC")  
	@SequenceGenerator(name="SEQ_RC", sequenceName = "SEQ_RC", allocationSize=1)
	private Long rctrId;
	
	@Column(name = "ASS_MAT")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;
	
	@Column(name = "RCTR_TRIM")
	private Short trimestre;
	
	@Column(name = "RCTR_ANNEE")
	private Short annee;
	
	@ManyToOne
	@JoinColumn(name = "CODE_TYPE_SALAIRE", referencedColumnName = "CODE_TYPE_SALAIRE")
	private TypeSalaire typeSalaire;	
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "EMP_MAT", referencedColumnName = "EMP_MAT"),
		@JoinColumn(name = "EMP_CLE", referencedColumnName = "EMP_CLE")
	})
	private Employeur employeur;	
	
	@Column(name = "RCTR_SALAIRE")
	private Long salaire;
	
	@Column(name = "RCTR_DT_SAISIE")
	private Date dateSaisie;
	
	@Column(name = "RCTR_DT_EXPLOITATION")
	private Date dateExploitation;
	
	@Column(name = "RCTR_MATAGENT")
	private Integer matriculeAgent;
	
	@Column(name = "RCTR_BR_SAISIE")
	private Short brSaisie;
	
	@Column(name = "RCTR_CODE_VALIDATION")
	private String codeValidation;
	
	
	
	@PrePersist
	void prePersist() {
		this.dateSaisie = new Date();
	}
	
	
	



	public Long getRctrId() {
		return rctrId;
	}
	public void setRctrId(Long rctrId) {
		this.rctrId = rctrId;
	}


	public Integer getAssMat() {
		return assMat;
	}
	public void setAssMat(Integer assMat) {
		this.assMat = assMat;
	}
	
	
	public Short getAssCle() {
		return assCle;
	}
	public void setAssCle(Short assCle) {
		this.assCle = assCle;
	}
	
	
	public Short getTrimestre() {
		return trimestre;
	}
	public void setTrimestre(Short trimestre) {
		this.trimestre = trimestre;
	}
	
	
	public Short getAnnee() {
		return annee;
	}
	public void setAnnee(Short annee) {
		this.annee = annee;
	}
	
	
	public TypeSalaire getTypeSalaire() {
		return typeSalaire;
	}
	public void setTypeSalaire(TypeSalaire typeSalaire) {
		this.typeSalaire = typeSalaire;
	}
	
	
	public Employeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	
	
	public Long getSalaire() {
		return salaire;
	}
	public void setSalaire(Long salaire) {
		this.salaire = salaire;
	}
	
	
	public Date getDateSaisie() {
		return dateSaisie;
	}
	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}
	
	
	public Date getDateExploitation() {
		return dateExploitation;
	}
	public void setDateExploitation(Date dateExploitation) {
		this.dateExploitation = dateExploitation;
	}


	public Integer getMatriculeAgent() {
		return matriculeAgent;
	}
	public void setMatriculeAgent(Integer matriculeAgent) {
		this.matriculeAgent = matriculeAgent;
	}


	public Short getBrSaisie() {
		return brSaisie;
	}
	public void setBrSaisie(Short brSaisie) {
		this.brSaisie = brSaisie;
	}


	public String getCodeValidation() {
		return codeValidation;
	}
	public void setCodeValidation(String codeValidation) {
		this.codeValidation = codeValidation;
	}
	
	
	
}
