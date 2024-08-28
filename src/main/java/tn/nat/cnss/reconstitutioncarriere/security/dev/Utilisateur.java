package tn.nat.cnss.reconstitutioncarriere.security.dev;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;




@Entity
@Table(name = "V_AUTHWEB_UTILISATEUR")
public class Utilisateur implements Serializable{
	
	private static final long serialVersionUID = -4707240905980756751L;

	@Id
	@Column(name = "usr_matricule")
	private String matricule;
	
	@Column(name = "usr_pwd")
	private String password;
	
	@Column(name = "usr_prenom")
	private String prenom;
	
	@Column(name = "usr_nom")
	private String nom;
	
	@ManyToOne
	@JoinColumn(name = "code_structure", referencedColumnName = "bur_cod")
	private Structure belongsTo;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "V_AUTHWEB_USER_ROLES", //schema = "AUTHWEB",
			joinColumns = 
				@JoinColumn(name="USER_ID", referencedColumnName="USR_MATRICULE"),
			inverseJoinColumns = {
				@JoinColumn(name="APR_ID", referencedColumnName="APR_ID")
			}
	)
	private List<ApplicationRole> rolesInApplication;
	
	
	
	@Transient
	@ManyToOne
	private Structure connectedFrom;
	
	@Transient
	@ManyToOne
	private Structure connectedAs;
	
	@Transient
	private String niceName;
	
	
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Structure getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(Structure belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	public Structure getConnectedFrom() {
		return connectedFrom;
	}
	public void setConnectedFrom(Structure connectedFrom) {
		this.connectedFrom = connectedFrom;
	}
	
	public Structure getConnectedAs() {
		return connectedAs;
	}
	public void setConnectedAs(Structure connectedAs) {
		this.connectedAs = connectedAs;
	}
	
	public List<ApplicationRole> getRolesInApplication() {
		return rolesInApplication;
	}
	public void setRolesInApplication(List<ApplicationRole> rolesInApplication) {
		this.rolesInApplication = rolesInApplication;
	}
	
	public String getNiceName() {
		return niceName;
	}
	public void setNiceName(String ninceName) {
		this.niceName = ninceName;
	}

}
