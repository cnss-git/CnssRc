package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ASSURE")
public class Assure implements Serializable{

	private static final long serialVersionUID = 5573975422814913591L;
	
	
	@EmbeddedId
	private AssureId id;
	
	@ManyToOne
	@JoinColumn(name = "ASS_DERREG", referencedColumnName = "REG_COD")
	private Regime regime;
	
	@Column(name = "ASS_DTEFF")
	private Date assDteff;
	
	@Column(name = "ASS_DTASSUJ")
	private Date assDtassuj;
	
	
	
	


	public AssureId getId() {
		return id;
	}
	public void setId(AssureId id) {
		this.id = id;
	}
	
	public Regime getRegime() {
		return regime;
	}
	
	public void setRegime(Regime regime) {
		this.regime = regime;
	}
	public Date getAssDteff() {
		return assDteff;
	}
	public void setAssDteff(Date assDteff) {
		this.assDteff = assDteff;
	}
	
	public Date getAssDtassuj() {
		return assDtassuj;
	}
	public void setAssDtassuj(Date assDtassuj) {
		this.assDtassuj = assDtassuj;
	}
	
	
	
	
	

}
