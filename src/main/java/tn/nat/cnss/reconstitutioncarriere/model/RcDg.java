package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "RC_DG")
public class RcDg implements Serializable{
	
	private static final long serialVersionUID = 1917745514583871463L;
	
	
	@Id
	@Column(name = "RCDG_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_RC")  
	@SequenceGenerator(name="SEQ_RC", sequenceName = "SEQ_RC", allocationSize=1)
	private Long rcdgId;
	
	@Column(name = "ASS_MAT")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;
	
	@Column(name = "RCDG_TRIM")
	private Short trimestre;
	
	@Column(name = "RCDG_ANNEE")
	private Short annee;
	
	@ManyToOne
	@JoinColumn(name = "CODE_TYPE_SALAIRE", referencedColumnName = "CODE_TYPE_SALAIRE")
	private TypeSalaire typeSalaire;
	
	@Column(name = "EMP_MAT")
	private Integer empMat;
	
	@Column(name = "EMP_CLE")
	private Short empCle;
	
	
	@Column(name = "RCDG_PAGE")
	private Integer page;
	
	@Column(name = "RCDG_LIGNE")
	private Short ligne;
	
	@Column(name = "RCDG_SALAIRE")
	private Long salaire;
	
	@Column(name = "RCDG_DT_SAISIE")
	private Date dateSaisie;
	
	@Column(name = "RCDG_MATAGENT")
	private Integer agent;
	
	@Column(name = "RCDG_DT_EXPLOITATION")
	private Date dateExploitation;
	
	@Column(name = "RCDG_DT_MODIFICATION")
	private Date dateModification;
	
	@Column(name = "RCDG_TYPE_OPERATION")
	private String typeOperation;
	
	@Column(name = "RCDG_BR_SAISIE")
	private Short brSaisie;
	
	@Column(name = "RCDG_OLD_SALAIRE")
	private Long oldSalaire;
	
	@ManyToOne
	@JoinColumn(name = "DEMANDE_ID", referencedColumnName = "DEMANDE_ID")
	private RcDemandeDg demandeDg;
	
	@Transient
	private String styleInDatatable;
	
	
	@PrePersist
	void prePersist() {
		this.dateSaisie = new Date();
	}
	@PreUpdate
	void preUpdate(){
		this.dateModification = new Date();
	}
	
	
	public Long getRcdgId() {
		return rcdgId;
	}
	public void setRcdgId(Long rcdgId) {
		this.rcdgId = rcdgId;
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
	
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	
	
	public Short getLigne() {
		return ligne;
	}
	public void setLigne(Short ligne) {
		this.ligne = ligne;
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
	
	
	public String getTypeOperation() {
		return typeOperation;
	}
	public void setTypeOperation(String typeOperation) {
		this.typeOperation = typeOperation;
	}
	
	
	public Short getBrSaisie() {
		return brSaisie;
	}
	public void setBrSaisie(Short brSaisie) {
		this.brSaisie = brSaisie;
	}
	
	
	public Short getEmpCle() {
		return empCle;
	}
	public void setEmpCle(Short empCle) {
		this.empCle = empCle;
	}
	
	
	public Integer getEmpMat() {
		return empMat;
	}
	public void setEmpMat(Integer empMat) {
		this.empMat = empMat;
	}
	
	
	public String getStyleInDatatable() {
		return styleInDatatable;
	}
	public void setStyleInDatatable(String styleInDatatable) {
		this.styleInDatatable = styleInDatatable;
	}
	
	
	public Long getOldSalaire() {
		return oldSalaire;
	}
	public void setOldSalaire(Long oldSalaire) {
		this.oldSalaire = oldSalaire;
	}
	
	
	public RcDemandeDg getDemandeDg() {
		return demandeDg;
	}
	public void setDemandeDg(RcDemandeDg demandeDg) {
		this.demandeDg = demandeDg;
	}
	

}
