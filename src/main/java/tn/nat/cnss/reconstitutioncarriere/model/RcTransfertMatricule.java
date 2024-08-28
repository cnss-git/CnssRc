package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "RC_TRANSFERT_MATRICULE")
public class RcTransfertMatricule implements Serializable{

	private static final long serialVersionUID = 8839684037476643078L;
	
	@Id
	@Column(name = "RCTR_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_RC")  
	@SequenceGenerator(name="SEQ_RC", sequenceName = "SEQ_RC", allocationSize=1)
	private Long rctrId;
	
	@Column(name = "ASS_MAT_SOURCE")
	private Integer assMatSource;
	
	@Column(name = "ASS_CLE_SOURCE")
	private Short assCleSource;
	
	@Column(name = "ASS_MAT_DESTINATION")
	private Integer assMatDestination;
	
	@Column(name = "ASS_CLE_DESTINATION")
	private Short assCleDestination;
	
	@Column(name = "RCTR_DT_SAISIE")
	private Date dateSaisie;
	
	@Column(name = "RCTR_DT_EXPLOITATION")
	private Date dateExploitation;
	
	@Column(name = "RCTR_MATAGENT")
	private Integer matriculeAgent;
	
	@Column(name = "RCTR_BR_SAISIE")
	private Short brSaisie;
	
	
	
	
	
	@PrePersist
	void prePersist() {
		this.dateSaisie = new Date();
	}





	public Long getRctrId() {
		return rctrId;
	}
	public void setRctrId(Long rctrId) {
		this.rctrId = rctrId;
	}


	public Integer getAssMatSource() {
		return assMatSource;
	}
	public void setAssMatSource(Integer assMatSource) {
		this.assMatSource = assMatSource;
	}


	public Short getAssCleSource() {
		return assCleSource;
	}
	public void setAssCleSource(Short assCleSource) {
		this.assCleSource = assCleSource;
	}
	

	public Integer getAssMatDestination() {
		return assMatDestination;
	}
	public void setAssMatDestination(Integer assMatDestination) {
		this.assMatDestination = assMatDestination;
	}


	public Short getAssCleDestination() {
		return assCleDestination;
	}
	public void setAssCleDestination(Short assCleDestination) {
		this.assCleDestination = assCleDestination;
	}
	

	public Date getDateSaisie() {
		return dateSaisie;
	}
	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}


	public Date getDateExploitation() {
		return dateExploitation;
	}
	public void setDateExploitation(Date dateExploitation) {
		this.dateExploitation = dateExploitation;
	}
	

	public Integer getMatriculeAgent() {
		return matriculeAgent;
	}
	public void setMatriculeAgent(Integer matriculeAgent) {
		this.matriculeAgent = matriculeAgent;
	}


	public Short getBrSaisie() {
		return brSaisie;
	}
	public void setBrSaisie(Short brSaisie) {
		this.brSaisie = brSaisie;
	}
	
	
	

}
