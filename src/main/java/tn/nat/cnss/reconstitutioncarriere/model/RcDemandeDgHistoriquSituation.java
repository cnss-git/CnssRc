package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RC_DEMANDE_DG_HIST_SIT")
public class RcDemandeDgHistoriquSituation implements Serializable{

	private static final long serialVersionUID = 6844552784125742407L;
	
	
	@Id
	@Column(name = "HIST_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_DEMANDE_DG_HIST")  
	@SequenceGenerator(name="SEQ_DEMANDE_DG_HIST", sequenceName = "SEQ_DEMANDE_DG_HIST", allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "DEMANDE_ID", referencedColumnName = "DEMANDE_ID")
	private RcDemandeDg demandeDg;
	
	@Column(name = "HIST_DATE")
	private Date dateSituation;
	
	@Column(name = "HIST_MATAGENT")
	private Integer matriculeAgent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SITUATION_CODE", referencedColumnName = "SITUATION_CODE")
	private RcDemandeDgSituation situation;
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public RcDemandeDg getDemandeDg() {
		return demandeDg;
	}
	public void setDemandeDg(RcDemandeDg demandeDg) {
		this.demandeDg = demandeDg;
	}
	

	public Date getDateSituation() {
		return dateSituation;
	}
	public void setDateSituation(Date dateSituation) {
		this.dateSituation = dateSituation;
	}
	

	public Integer getMatriculeAgent() {
		return matriculeAgent;
	}
	public void setMatriculeAgent(Integer matriculeAgent) {
		this.matriculeAgent = matriculeAgent;
	}
	
	
	public RcDemandeDgSituation getSituation() {
		return situation;
	}
	public void setSituation(RcDemandeDgSituation situation) {
		this.situation = situation;
	}
	

}
