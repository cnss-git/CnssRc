package tn.nat.cnss.reconstitutioncarriere.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "BENEFICIAIRE")
public class Beneficiaire implements Serializable{

	private static final long serialVersionUID = -2171132180422726019L;
	
	@Id
	@Column(name = "BEN_IDUCNSS")
	private Long benIducnss;
	
	
	@Column(name = "ASS_MAT", insertable = false, updatable = false)
	private Integer assMat;
	
	@Column(name = "ASS_CLE", insertable = false, updatable = false)
	private Short assCle;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "ASS_MAT", referencedColumnName = "ASS_MAT", insertable = false, updatable = false),
		@JoinColumn(name = "ASS_CLE", referencedColumnName = "ASS_CLE", insertable = false, updatable = false)
	})
	private Assure assure;
	

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "ASS_MAT", referencedColumnName = "ASS_RACIN", insertable = false, updatable = false),
		@JoinColumn(name = "ASS_CLE", referencedColumnName = "ASS_CLE", insertable = false, updatable = false),
		@JoinColumn(name = "BEN_TYPE", referencedColumnName = "ASS_TYPE", insertable = false, updatable = false)
	})
	private AssureCress assureCress;
	
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "BEN_IDUCNSS", referencedColumnName = "BEN_IDUCNSS")
	@OrderBy("SIT_DTEFF ASC, SIT_COD ASC")
	private List<SituationAss> histSituations = new ArrayList<SituationAss>();
	
	@Column(name = "BEN_TYPE")
	private Short benType;
	
	@Column(name = "BEN_RANG")
	private Short benRang;
	
	@Column(name = "BEN_PRENOM")
	private String benPrenomFr;
	
	@Column(name = "BEN_PRN_AR")
	private String benPrenomAr;
	
	@Column(name = "BEN_NOM")
	private String benNomFr;
	
	@Column(name = "BEN_NOM_AR")
	private String benNomAr;
	
	@Column(name = "BEN_PRNPER")
	private String benPrenomPereFr;
	
	@Column(name = "BEN_PRNPER_AR")
	private String benPrenomPereAr;
	
	@Column(name = "BEN_PRNGP")
	private String benPrenomGrandpereFr;
	
	@Column(name = "BEN_PRNGP_AR")
	private String benPrenomGrandpereAr;
	
	
	
	@Column(name = "BEN_DTNAIS")
	private Date benDtnais;
	
	
	
	

	public Long getBenIducnss() {
		return benIducnss;
	}
	public void setBenIducnss(Long benIducnss) {
		this.benIducnss = benIducnss;
	}
	
	public Integer getAssMat() {
		return assMat;
	}
	public void setAssMat(Integer assMat) {
		this.assMat = assMat;
	}

	public Short getAssCle() {
		return assCle;
	}
	public void setAssCle(Short assCle) {
		this.assCle = assCle;
	}	

	public Assure getAssure() {
		return assure;
	}
	public void setAssure(Assure assure) {
		this.assure = assure;
	}
	
	public AssureCress getAssureCress() {
		return assureCress;
	}
	public void setAssureCress(AssureCress assureCress) {
		this.assureCress = assureCress;
	}
	
	public Short getBenType() {
		return benType;
	}
	public void setBenType(Short benType) {
		this.benType = benType;
	}

	public Short getBenRang() {
		return benRang;
	}
	public void setBenRang(Short benRang) {
		this.benRang = benRang;
	}
	
	public String getBenPrenomFr() {
		return benPrenomFr;
	}
	public void setBenPrenomFr(String benPrenomFr) {
		this.benPrenomFr = benPrenomFr;
	}
	
	public String getBenPrenomAr() {
		return benPrenomAr;
	}
	public void setBenPrenomAr(String benPrenomAr) {
		this.benPrenomAr = benPrenomAr;
	}
	
	public String getBenNomFr() {
		return benNomFr;
	}
	public void setBenNomFr(String benNomFr) {
		this.benNomFr = benNomFr;
	}
	
	public String getBenNomAr() {
		return benNomAr;
	}
	public void setBenNomAr(String benNomAr) {
		this.benNomAr = benNomAr;
	}
	
	public String getBenPrenomPereFr() {
		return benPrenomPereFr;
	}
	public void setBenPrenomPereFr(String benPrenomPereFr) {
		this.benPrenomPereFr = benPrenomPereFr;
	}
	
	public String getBenPrenomPereAr() {
		return benPrenomPereAr;
	}
	public void setBenPrenomPereAr(String benPrenomPereAr) {
		this.benPrenomPereAr = benPrenomPereAr;
	}
	
	public String getBenPrenomGrandpereFr() {
		return benPrenomGrandpereFr;
	}
	public void setBenPrenomGrandpereFr(String benPrenomGrandpereFr) {
		this.benPrenomGrandpereFr = benPrenomGrandpereFr;
	}
	
	public String getBenPrenomGrandpereAr() {
		return benPrenomGrandpereAr;
	}
	public void setBenPrenomGrandpereAr(String benPrenomGrandpereAr) {
		this.benPrenomGrandpereAr = benPrenomGrandpereAr;
	}
	
	public Date getBenDtnais() {
		return benDtnais;
	}
	public void setBenDtnais(Date benDtnais) {
		this.benDtnais = benDtnais;
	}
	
	public List<SituationAss> getHistSituations() {
		return histSituations;
	}
	public void setHistSituations(List<SituationAss> histSituations) {
		this.histSituations = histSituations;
	}
	
	

}
