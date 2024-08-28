package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SalaireTrimId implements Serializable{
	
	private static final long serialVersionUID = 43372103515151678L;
	
	@ManyToOne
	@JoinColumn(name = "CODE_TYPE_SALAIRE", referencedColumnName = "CODE_TYPE_SALAIRE")
	private TypeSalaire typeSalaire;
	
	@Column(name = "ASS_MAT")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;
	
	@Column(name = "REG_COD")
	private Short regCod;
	
	@Column(name = "CEX_COD")
	private Short cexCod;
	
	@Column(name = "ANNEE")
	private Short annee;
	
	@Column(name = "TRIMESTRE")
	private Short trimestre;
	
	@Column(name = "NUM_SEQ_CARR")
	private Integer numSeqCarr;
	
	
	//---
	public TypeSalaire getTypeSalaire() {
		return typeSalaire;
	}
	public void setTypeSalaire(TypeSalaire typeSalaire) {
		this.typeSalaire = typeSalaire;
	}
	
	//---
	public Integer getAssMat() {
		return assMat;
	}
	public void setAssMat(Integer assMat) {
		this.assMat = assMat;
	}
	
	//---
	public Short getAssCle() {
		return assCle;
	}
	public void setAssCle(Short assCle) {
		this.assCle = assCle;
	}
	
	//---
	public Short getRegCod() {
		return regCod;
	}
	public void setRegCod(Short regCod) {
		this.regCod = regCod;
	}
	
	//---
	public Short getCexCod() {
		return cexCod;
	}
	public void setCexCod(Short cexCod) {
		this.cexCod = cexCod;
	}
	
	//---
	public Short getAnnee() {
		return annee;
	}
	public void setAnnee(Short annee) {
		this.annee = annee;
	}
	
	//---
	public Short getTrimestre() {
		return trimestre;
	}
	public void setTrimestre(Short trimestre) {
		this.trimestre = trimestre;
	}
	
	//---
	public Integer getNumSeqCarr() {
		return numSeqCarr;
	}
	public void setNumSeqCarr(Integer numSeqCarr) {
		this.numSeqCarr = numSeqCarr;
	}
	

}
