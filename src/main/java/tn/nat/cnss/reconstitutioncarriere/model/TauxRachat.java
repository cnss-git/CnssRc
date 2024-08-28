package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RC_TAUX_RACHAT")
public class TauxRachat implements Serializable{

	private static final long serialVersionUID = -1274487586308997429L;
	
	@Id
	@Column(name = "TAURACH_AGE_MIN")
	private Short ageMin;
	
	@Column(name = "TAURACH_AGE_MAX")
	private Short ageMax;
	
	@Column(name = "TAURACH_TAUX")
	private Short taux;

	
	
	
	
	
	
	public Short getAgeMin() {
		return ageMin;
	}
	public void setAgeMin(Short ageMin) {
		this.ageMin = ageMin;
	}

	public Short getAgeMax() {
		return ageMax;
	}
	public void setAgeMax(Short ageMax) {
		this.ageMax = ageMax;
	}

	public Short getTaux() {
		return taux;
	}
	public void setTaux(Short taux) {
		this.taux = taux;
	}
	
	

}
