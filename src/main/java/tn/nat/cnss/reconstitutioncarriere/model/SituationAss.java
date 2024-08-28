package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SITUATION_ASS")
public class SituationAss implements Serializable{

	private static final long serialVersionUID = 8471160611070345634L;
	
	@EmbeddedId
	private SituationAssId id;
	
	

	public SituationAssId getId() {
		return id;
	}
	public void setId(SituationAssId id) {
		this.id = id;
	}
	
	

}
