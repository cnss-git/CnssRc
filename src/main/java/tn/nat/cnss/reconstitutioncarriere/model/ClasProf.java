package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CLAS_PROF")
public class ClasProf implements Serializable{

	private static final long serialVersionUID = -4232597071988689576L;
	
	
	
	@EmbeddedId
	private ClasProdId id;
	
	
	

	public ClasProdId getId() {
		return id;
	}
	public void setId(ClasProdId id) {
		this.id = id;
	}
	

}
