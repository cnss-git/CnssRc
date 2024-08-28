package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SituationAssId implements Serializable{

	private static final long serialVersionUID = -6536629487386157484L;
	
	
	@Column(name = "BEN_IDUCNSS")
	private Long benIducnss;
	
	@Column(name = "SIT_COD")
	private Short sitCod;
	
	@Column(name = "SIT_DTEFF")
	private Date sitDteff;
	
	

	public Long getBenIducnss() {
		return benIducnss;
	}
	public void setBenIducnss(Long benIducnss) {
		this.benIducnss = benIducnss;
	}
	
	

	public Short getSitCod() {
		return sitCod;
	}
	public void setSitCod(Short sitCod) {
		this.sitCod = sitCod;
	}
	
	

	public Date getSitDteff() {
		return sitDteff;
	}
	public void setSitDteff(Date sitDteff) {
		this.sitDteff = sitDteff;
	}
	
	
	
	
	
	

	
	

}
