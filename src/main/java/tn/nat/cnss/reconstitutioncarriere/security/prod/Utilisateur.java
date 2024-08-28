package tn.nat.cnss.reconstitutioncarriere.security.prod;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "V_AUTHWEB_UTILISATEUR")
public class Utilisateur implements Serializable{

	private static final long serialVersionUID = 8174729600619751261L;
	
	
	@Id
	@Column(name = "USR_MATRICULE")
	private Integer matricule;
	
	
	@ManyToOne
	@JoinColumn(name = "CODE_STRUCTURE", referencedColumnName = "BUR_COD")
	private Structure belongsTo;


	public Integer getMatricule() {
		return matricule;
	}
	public void setMatricule(Integer matricule) {
		this.matricule = matricule;
	}


	public Structure getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(Structure belongsTo) {
		this.belongsTo = belongsTo;
	}
	

}
