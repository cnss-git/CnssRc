package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "REGIME")
public class Regime implements Serializable{

	private static final long serialVersionUID = -3747170835056632200L;
	
	
	@Id
	@Column (name = "REG_COD")
	private Short regCod;
	
	@Column (name = "REG_LIB")
	private String regLib;
	
	@Column(name = "REG_TYP")
	private Short typeRegime;

	
	
	public Short getRegCod() {
		return regCod;
	}
	public void setRegCod(Short regCod) {
		this.regCod = regCod;
	}

	public String getRegLib() {
		return regLib;
	}
	public void setRegLib(String regLib) {
		this.regLib = regLib;
	}
	
	public Short getTypeRegime() {
		return typeRegime;
	}
	public void setTypeRegime(Short typeRegime) {
		this.typeRegime = typeRegime;
	}
	
	
	
	

}
