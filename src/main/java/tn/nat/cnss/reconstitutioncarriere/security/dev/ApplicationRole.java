package tn.nat.cnss.reconstitutioncarriere.security.dev;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "V_AUTHWEB_APPLICATIONS_ROLES")
public class ApplicationRole implements Serializable{

	private static final long serialVersionUID = -3119008482786795043L;
	
	
	@Id
	@Column(name = "APR_ID")
	private Integer id;	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APP_ID", referencedColumnName = "APP_ID")
	private Application application;
	
	@Column(name = "APR_NOM")
	private String springSecurityRoleCode;
	
	@Column(name = "APR_LIB")
	private String roleLibelle;
	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	
	public String getSpringSecurityRoleCode() {
		return springSecurityRoleCode;
	}
	public void setSpringSecurityRoleCode(String springSecurityRoleCode) {
		this.springSecurityRoleCode = springSecurityRoleCode;
	}

	public String getRoleLibelle() {
		return roleLibelle;
	}
	public void setRoleLibelle(String roleLibelle) {
		this.roleLibelle = roleLibelle;
	}
	
	
	
	
	

}
