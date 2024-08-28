package tn.nat.cnss.reconstitutioncarriere.security.dev;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_AUTHWEB_APPLICATION")
public class Application implements Serializable{

	private static final long serialVersionUID = 6569001461694766634L;
	
	@Id
	@Column(name = "APP_ID")	
	private Short appId;
	
	@Column(name = "APP_NOM")
	private String appNom;
	
	
	

	public Short getAppId() {
		return appId;
	}
	public void setAppId(Short appId) {
		this.appId = appId;
	}
	
	
	
	public String getAppNom() {
		return appNom;
	}
	public void setAppNom(String appNom) {
		this.appNom = appNom;
	}
	
	
	
	

}
