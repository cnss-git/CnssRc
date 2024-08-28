package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class AssureCressId implements Serializable{

	private static final long serialVersionUID = -5133528509591734407L;
	
	@Column(name = "ASS_RACIN")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;
	
	@Column(name = "ASS_TYPE")
	private Short assType;
	

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
	
	
	public Short getAssType() {
		return assType;
	}
	public void setAssType(Short assType) {
		this.assType = assType;
	}

}
