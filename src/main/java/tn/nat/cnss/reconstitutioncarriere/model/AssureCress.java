package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "ASSURE_CRESS")
public class AssureCress implements Serializable{

	private static final long serialVersionUID = 9092488761738481378L;
	
	@EmbeddedId
	private AssureCressId id;
	
	@Column(name = "ID")
	private Long iu;
	
	
	

	public AssureCressId getId() {
		return id;
	}
	public void setId(AssureCressId id) {
		this.id = id;
	}

	public Long getIu() {
		return iu;
	}
	public void setIu(Long iu) {
		this.iu = iu;
	}
	
	
	
	
	
	

}
