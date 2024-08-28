package tn.nat.cnss.reconstitutioncarriere.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;


@Entity
@SqlResultSetMapping(
	name = "LigneCarriereDTOMapping",
	entities=@EntityResult(
		entityClass = ZLigneCarriereDto.class
	)
)
public class ZLigneCarriereDto implements Serializable{

	private static final long serialVersionUID = 7971408212686590551L;
	
	public static final String NATIVE_QUERY_STRING = 
			
	"select   " + 
	"to_char(carriere.ass_mat,'FM00000000')||to_char(carriere.ass_cle,'FM00')||trimestre||annee||to_char(carriere.emp_mat,'FM00000000')||to_char(carriere.emp_cle,'FM00')||to_char(carriere.code_type_salaire,'FM00') id,   " + 
	"carriere.source, "+
	"carriere.ass_mat ass_mat, carriere.ass_cle ass_cle, carriere.trimestre trimestre, carriere.annee annee,    " + 
	"carriere.emp_mat emp_mat, carriere.emp_cle emp_cle, carriere.code_type_salaire code_type_salaire, type_salaire.libelle_ar libelle_type_salaire,  ass.ass_derreg ass_derreg, max(trunc(carriere.date_saisie)) date_saisie, " + 
	"sum(carriere.salaire) salaire   " + 
	"from   " + 
	"(   " + 
	"    select    " + 
	"        'carriere' source, "+
	"        ca.ass_mat ass_mat, ca.ass_cle ass_cle,  ca.trimestre trimestre, ca.annee annee,    " + 
	"        ca.emp_mat emp_mat, ca.emp_cle emp_cle, ca.code_type_salaire code_type_salaire,    " + 
	"        ca.salaire salaire, ca.date_saisie date_saisie   " + 
	"    from carriere_assure_old ca   " + 
	"    where ca.ass_mat = :ass_mat   " + 
	"    and ca.ass_cle = :ass_cle   " + 
	"    and not exists    " + 
	"    (   " + 
	"        select 1 from    " + 
	"        rc_dg dg    " + 
	"        where dg.ass_mat = ca.ass_mat    " + 
	"        and dg.ass_cle = ca.ass_cle    " + 
	"        and dg.rcdg_trim = ca.trimestre    " + 
	"        and dg.rcdg_annee = ca.annee   " + 
	"        and dg.emp_mat = ca.emp_mat   " + 
	"        and dg.emp_cle = ca.emp_cle   " + 
	"        and dg.code_type_salaire = ca.code_type_salaire "+
	"        and dg.rcdg_dt_exploitation is null   " + 
	"    )   " + 
	"    union all   " + 
	"    select    " + 
	"       'dg' source, "+
	"        dg.ass_mat ass_mat, dg.ass_cle ass_cle, dg.rcdg_trim trimestre,  dg.rcdg_annee annee,    " + 
	"        dg.emp_mat emp_mat, dg.emp_cle emp_cle, dg.code_type_salaire code_type_salaire,    " + 
	"        dg.rcdg_salaire salaire, dg.rcdg_dt_saisie date_saisie   " + 
	"    from rc_dg dg   " + 
	"    where dg.ass_mat = :ass_mat   " + 
	"    and dg.ass_cle = :ass_cle   " + 
	"    and dg.rcdg_id =    " + 
	"    (   " + 
	"        select max(dg2.rcdg_id)    " + 
	"        from rc_dg dg2    " + 
	"        where dg2.ass_mat = dg.ass_mat    " + 
	"        and dg2.ass_cle = dg.ass_cle    " + 
	"        and dg2.rcdg_trim = dg.rcdg_trim   " + 
	"        and dg2.rcdg_annee = dg.rcdg_annee   " + 
	"        and dg2.emp_mat = dg.emp_mat    " + 
	"        and dg2.emp_cle = dg.emp_cle   " + 
	"        and dg2.code_type_salaire = dg.code_type_salaire " +
	"    )   " + 
	"    and dg.rcdg_dt_exploitation is null   " + 
	//"    and dg.rcdg_type_operation <> 61   " + 
	"       " + 
	") carriere,    " + 
	"type_salaire type_salaire,       " + 
	"assure ass "+
	"where type_salaire.code_type_salaire = carriere.code_type_salaire   " +
	"and ass.ass_mat = :ass_mat "+
	"and ass.ass_cle = :ass_cle "+
	"group by    " + 
	"to_char(carriere.ass_mat,'FM00000000')||to_char(carriere.ass_cle,'FM00')||trimestre||annee||to_char(carriere.emp_mat,'FM00000000')||to_char(carriere.emp_cle,'FM00')||to_char(carriere.code_type_salaire,'FM00'),   " +
	"carriere.source, "+
	"carriere.ass_mat, carriere.ass_cle, carriere.trimestre, carriere.annee,    " + 
	"carriere.emp_mat, carriere.emp_cle, carriere.code_type_salaire, type_salaire.libelle_ar, ass_derreg   " + 
	"order by annee, trimestre";
	
			
			
	
	@Id
	public String id;
	
	@Column(name = "source")
	public String source;
	
	@Column(name = "ass_mat")
	public Integer assMat;
	
	@Column(name = "ass_cle")
	public Short assCle;
	
	@Column(name = "trimestre")
	public Short trimestre;
	
	@Column(name = "annee")
	public Short annee;
	
	@Column(name = "emp_mat")
	public Integer empMat;
	
	@Column(name = "emp_cle")
	public Short empCle;
	
	@Column(name = "code_type_salaire")
	public Short codeTypeSalaire;
	
	@Column(name = "libelle_type_salaire")
	public String libelleTypeSalaire;
	
	@Column(name = "ass_derreg")
	private Short regime;
	
	@Column(name = "salaire")
	public Long salaire;
	
	@Column(name = "date_saisie")
	private Date dateSaisie;
	
	@Transient
	private String styleInDatatable;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
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
	
	
	public Short getTrimestre() {
		return trimestre;
	}
	public void setTrimestre(Short trimestre) {
		this.trimestre = trimestre;
	}
	
	
	public Short getAnnee() {
		return annee;
	}
	public void setAnnee(Short annee) {
		this.annee = annee;
	}
	
	
	public Integer getEmpMat() {
		return empMat;
	}
	public void setEmpMat(Integer empMat) {
		this.empMat = empMat;
	}
	
	
	public Short getEmpCle() {
		return empCle;
	}
	public void setEmpCle(Short empCle) {
		this.empCle = empCle;
	}
	
	
	public Short getCodeTypeSalaire() {
		return codeTypeSalaire;
	}
	public void setCodeTypeSalaire(Short codeTypeSalaire) {
		this.codeTypeSalaire = codeTypeSalaire;
	}
	
	
	public String getLibelleTypeSalaire() {
		return libelleTypeSalaire;
	}
	public void setLibelleTypeSalaire(String libelleTypeSalaire) {
		this.libelleTypeSalaire = libelleTypeSalaire;
	}
	
	
	public Short getRegime() {
		return regime;
	}
	public void setRegime(Short regime) {
		this.regime = regime;
	}
	
	
	public Long getSalaire() {
		return salaire;
	}
	public void setSalaire(Long salaire) {
		this.salaire = salaire;
	}
	
	
	public Date getDateSaisie() {
		return dateSaisie;
	}
	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}
	
	
	public String getStyleInDatatable() {
		return styleInDatatable;
	}
	public void setStyleInDatatable(String styleInDatatable) {
		this.styleInDatatable = styleInDatatable;
	}
	
	
	
	
	
}
