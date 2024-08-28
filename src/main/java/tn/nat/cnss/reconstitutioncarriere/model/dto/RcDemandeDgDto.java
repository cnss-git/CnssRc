package tn.nat.cnss.reconstitutioncarriere.model.dto;

import java.io.Serializable;
import java.util.Date;

public class RcDemandeDgDto implements Serializable{

	private static final long serialVersionUID = -4701551789865933296L;
	
	private Long id;
	private Integer assMat;
	private Short assCle;
	private Short objetCode;
	private String objetLibelle;
	private Short situationCode;
	private String situationLibelle;
	private Date dateSituation;
	
	public RcDemandeDgDto() {
		super();
	}
	
	
	
	
	
	
	public RcDemandeDgDto(Long id, Integer assMat, Short assCle, Short objetCode, String objetLibelle, Short situationCode, String situationLibelle, Date dateSituation) {
		super();
		this.id = id;
		this.assMat = assMat;
		this.assCle = assCle;
		this.objetCode = objetCode;
		this.objetLibelle = objetLibelle;
		this.situationCode = situationCode;
		this.situationLibelle = situationLibelle;
		this.dateSituation = dateSituation;
	}






	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Short getObjetCode() {
		return objetCode;
	}
	public void setObjetCode(Short objetCode) {
		this.objetCode = objetCode;
	}
	public String getObjetLibelle() {
		return objetLibelle;
	}
	public void setObjetLibelle(String objetLibelle) {
		this.objetLibelle = objetLibelle;
	}
	public Short getSituationCode() {
		return situationCode;
	}
	public void setSituationCode(Short situationCode) {
		this.situationCode = situationCode;
	}
	public String getSituationLibelle() {
		return situationLibelle;
	}
	public void setSituationLibelle(String situationLibelle) {
		this.situationLibelle = situationLibelle;
	}
	public Date getDateSituation() {
		return dateSituation;
	}
	public void setDateSituation(Date dateSituation) {
		this.dateSituation = dateSituation;
	}
	
	

}
