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
@Table(name = "RC_JUGEMENT")
public class RcJugement implements Serializable{

	private static final long serialVersionUID = -4297858218943549492L;
	
	@Id
	@Column(name = "RCJUG_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_RC")  
	@SequenceGenerator(name="SEQ_RC", sequenceName = "SEQ_RC", allocationSize=1)
	private Long rcjugId;
	
	@Column(name = "ASS_MAT")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;
	
	@Column(name = "RCJUG_TRIM")
	private Short trimestre;
	
	@Column(name = "RCJUG_ANNEE")
	private Short annee;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "EMP_MAT", referencedColumnName = "EMP_MAT"),
		@JoinColumn(name = "EMP_CLE", referencedColumnName = "EMP_CLE")
	})
	private Employeur employeur;
	
	@Column(name = "RCJUG_SALAIRE")
	private Long salaire;	
	
	@Column(name = "RCJUG_DT_SAISIE")
	private Date dateSaisie;
	
	@Column(name = "RCJUG_MATAGENT")
	private Integer agent;
	
	@Column(name = "RCJUG_DT_EXPLOITATION")
	private Date dateExploitation;
	
	@Column(name = "RCJUG_DT_MODIFICATION")
	private Date dateModification;
	
	@Column(name = "RCJUG_BR_SAISIE")
	private Short brSaisie;
	
	
	
	
	@PrePersist
	void prePersist() {
		this.dateSaisie = new Date();
	}
	@PreUpdate
	void preUpdate(){
		this.dateModification = new Date();
	}
	
	
	

	public Long getRcjugId() {
		return rcjugId;
	}
	public void setRcjugId(Long rcjugId) {
		this.rcjugId = rcjugId;
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
	

	public Integer getAgent() {
		return agent;
	}
	public void setAgent(Integer agent) {
		this.agent = agent;
	}
	

	public Date getDateExploitation() {
		return dateExploitation;
	}
	public void setDateExploitation(Date dateExploitation) {
		this.dateExploitation = dateExploitation;
	}
	

	public Date getDateModification() {
		return dateModification;
	}
	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}
	
	
	public Short getBrSaisie() {
		return brSaisie;
	}
	public void setBrSaisie(Short brSaisie) {
		this.brSaisie = brSaisie;
	}
	
	

}
