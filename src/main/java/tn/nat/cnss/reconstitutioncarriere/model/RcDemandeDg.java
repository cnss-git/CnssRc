package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;

@Entity
@Table(name = "RC_DEMANDE_DG")
public class RcDemandeDg implements Serializable{

	private static final long serialVersionUID = 1077589382012924861L;
	
	@Id
	@Column(name = "DEMANDE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_DEMANDE_DG")  
	@SequenceGenerator(name="SEQ_DEMANDE_DG", sequenceName = "SEQ_DEMANDE_DG", allocationSize=1)
	private Long id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "ASS_MAT", referencedColumnName = "ASS_MAT"),
		@JoinColumn(name = "ASS_CLE", referencedColumnName = "ASS_CLE")
	})
	private Assure assure;
	
	@ManyToOne
	@JoinColumn(name = "OBJET_CODE", referencedColumnName = "OBJET_CODE")
	private RcDemandeDgObjet objetDemande;
	
	@ManyToOne
	@JoinColumn(name = "BUR_COD", referencedColumnName = "BUR_COD")
	private Structure bureau;
	
	/*
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "DEMANDE_ID", referencedColumnName = "DEMANDE_ID")
	*/
	@OneToMany(mappedBy = "demandeDg", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}/*, orphanRemoval = true*/, fetch = FetchType.EAGER)
	@OrderBy("id")
	private List<RcDemandeDgHistoriquSituation> historiqueSituation; 
	
	/*
	@Transient
	private RcDemandeDgHistoriquSituation situationActuelle;
	*/
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public Assure getAssure() {
		return assure;
	}
	public void setAssure(Assure assure) {
		this.assure = assure;
	}
	

	public RcDemandeDgObjet getObjetDemande() {
		return objetDemande;
	}
	public void setObjetDemande(RcDemandeDgObjet objetDemande) {
		this.objetDemande = objetDemande;
	}
	

	public Structure getBureau() {
		return bureau;
	}
	public void setBureau(Structure bureau) {
		this.bureau = bureau;
	}
	
	
	public List<RcDemandeDgHistoriquSituation> getHistoriqueSituation() {
		return historiqueSituation;
	}
	public void setHistoriqueSituation(List<RcDemandeDgHistoriquSituation> historiqueSituation) {
		this.historiqueSituation = historiqueSituation;
	}
	
	/*
	public RcDemandeDgHistoriquSituation getSituationActuelle() {		
		return situationActuelle;
	}
	public void setSituationActuelle(RcDemandeDgHistoriquSituation situationActuelle) {
		this.situationActuelle = situationActuelle;
	}
	*/
	
	/*
	@PostLoad
	void loadTransientAttributes() {
		situationActuelle = getHistoriqueSituation()
				.stream()
				.sorted(
						Comparator.comparing(RcDemandeDgHistoriquSituation::getDateSituation)
				)
				.collect(Collectors.toList())
				.get(getHistoriqueSituation().size()-1);
	}
	*/
	

}
