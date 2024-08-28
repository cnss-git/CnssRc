package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClasProdId implements Serializable{

	private static final long serialVersionUID = -4413120435344745567L;
	
	
	
	@Column(name = "ASS_MAT")
	private Integer assMat;
	
	@Column(name = "ASS_CLE")
	private Short assCle;
	
	@Column(name = "REG_COD")
	private Short regCod;
	
	@Column(name = "CLA_COD")
	private Short claCod;
	
	@Column(name = "CPI_DTDEBCF")
	private Date dateDepart;
	
	
	
	

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
	

	public Short getRegCod() {
		return regCod;
	}
	public void setRegCod(Short regCod) {
		this.regCod = regCod;
	}
	

	public Short getClaCod() {
		return claCod;
	}
	public void setClaCod(Short claCod) {
		this.claCod = claCod;
	}
	

	public Date getDateDepart() {
		return dateDepart;
	}
	public void setDateDepart(Date dateDepart) {
		this.dateDepart = dateDepart;
	}
	
	

}
