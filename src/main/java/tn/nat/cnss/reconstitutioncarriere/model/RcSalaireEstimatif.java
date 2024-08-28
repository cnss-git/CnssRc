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
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "RC_SALAIRE_ESTIMATIF")
public class RcSalaireEstimatif implements Serializable{

	private static final long serialVersionUID = -3751005772647470068L;
	
	
	@Id
	@Column(name = "RCSE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_RC")  
	@SequenceGenerator(name="SEQ_RC", sequenceName = "SEQ_RC", allocationSize=1)
	private Long rcseId;
	
	@Column(name = "ASS_MAT")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;
	
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "EMP_MAT", referencedColumnName = "EMP_MAT"),
		@JoinColumn(name = "EMP_CLE", referencedColumnName = "EMP_CLE")
	})			
	private Employeur employeur;
	
	@Column(name = "RCSE_TRIM")
	private Short trimestre;
	
	@Column(name = "RCSE_ANNEE")
	private Short annee;
	
	@Column(name = "RCSE_SALAIRE")
	private Long salaire;
	
	@Column(name = "RCSE_DT_SAISIE")
	private Date dateSaisie;
	
	@Column(name = "RCSE_DT_EXPLOITATION")
	private Date dateExploitation;
	
	@Column(name = "RCSE_MATAGENT")
	private Integer matriculeAgent;
	
	@Column(name = "RCSE_BR_SAISIE")
	private Short brSaisie;
	
	
	@PrePersist
	void prePersist() {
		this.dateSaisie = new Date();
	}
	@PreUpdate
	void preUpdate(){
		this.dateSaisie = new Date();
	}
	

	


	public Long getRcseId() {
		return rcseId;
	}
	public void setRcseId(Long rcseId) {
		this.rcseId = rcseId;
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


	public Employeur getEmployeur() {
		return employeur;
	}
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
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
	
	
}
