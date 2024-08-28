package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class AssureId implements Serializable{

	private static final long serialVersionUID = -5133528509591734407L;
	
	@Column(name = "ASS_MAT")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;

	public AssureId() {
		super();
	}

	public AssureId(Integer assMat, Short assCle) {
		super();
		this.assMat = assMat;
		this.assCle = assCle;
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
	

}
