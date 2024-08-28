package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class CarriereAssureId implements Serializable{

	private static final long serialVersionUID = 8937873444095924659L;
	
	
	@Column(name = "CODE_TYPE_SALAIRE")
	private Short typeSalaire;
	
	@Column(name = "ASS_MAT")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;
	
	@Column(name = "REG_COD")
	private Short regime;
	
	@Column(name = "CEX_COD")
	private Short codeExpl;
	
	@Column(name = "TRIMESTRE")
	private Short trimestre;
	
	@Column(name = "ANNEE")
	private Short annee;
	
	@Column(name = "NUM_SEQ_CARR")
	private Long numeroSequentiel;
	
	
	
	
	
	

	public Short getTypeSalaire() {
		return typeSalaire;
	}
	public void setTypeSalaire(Short typeSalaire) {
		this.typeSalaire = typeSalaire;
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
	

	public Short getRegime() {
		return regime;
	}
	public void setRegime(Short regime) {
		this.regime = regime;
	}
	
	
	public Short getCodeExpl() {
		return codeExpl;
	}
	public void setCodeExpl(Short codeExpl) {
		this.codeExpl = codeExpl;
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
	

	public Long getNumeroSequentiel() {
		return numeroSequentiel;
	}
	public void setNumeroSequentiel(Long numeroSequentiel) {
		this.numeroSequentiel = numeroSequentiel;
	}
	

}
