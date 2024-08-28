package tn.nat.cnss.reconstitutioncarriere.model.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssureDto {
	
	private Integer assMat;
	private Short	assCle;
	private String 	benPrenomFr;
	private String 	benPrenomAr;
	private String	benNomFr;
	private String	benNomAr;
	private String	benPrenomPereFr;
	private String	benPrenomPereAr;
	private String	benPrenomGrandpereFr;
	private String	benPrenomGrandpereAr;
	private String 	identiteFr;
	private String	identiteAr;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date	benDtnais;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date	assDteff;
	private Date	assDtassuj;
	@JsonProperty(value = "id")
	private Long 	iu;
	private Short 	regCod;
	private Short age;
	private Short typeRegime;
	private Short categorieTns;
	private Short situation;
	
	
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
	
	public String getIdentiteFr() {
		identiteFr = benPrenomFr;
		if (benPrenomPereFr != null) {
			identiteFr = identiteFr.concat(" B ").concat(benPrenomPereFr);
			if (benPrenomGrandpereFr != null) {
				identiteFr = identiteFr.concat(" B ").concat(benPrenomGrandpereFr);
			}
		}
		identiteFr = identiteFr.concat(" ").concat(benNomFr);
		return identiteFr;
	}
	public void setIdentiteFr(String identiteFr) {
		this.identiteFr = identiteFr;
	}
	
	public String getIdentiteAr() {
		identiteAr = benPrenomAr;
		if (benPrenomPereAr != null) {
			identiteAr = identiteAr.concat(" بن ").concat(benPrenomPereAr);
			if (benPrenomGrandpereAr != null) {
				identiteAr = identiteAr.concat(" بن ").concat(benPrenomGrandpereAr);
			}
		}
		identiteAr = identiteAr.concat(" ").concat(benNomAr);
		return identiteAr;
	}
	public void setIdentiteAr(String identiteAr) {
		this.identiteAr = identiteAr;
	}
	
	public Date getBenDtnais() {
		return benDtnais;
	}
	public void setBenDtnais(Date benDtnais) {
		this.benDtnais = benDtnais;
	}
	
	
	public Date getAssDteff() {
		return assDteff;
	}
	public void setAssDteff(Date assDteff) {
		this.assDteff = assDteff;
	}
	
	
	public Date getAssDtassuj() {
		return assDtassuj;
	}
	public void setAssDtassuj(Date assDtassuj) {
		this.assDtassuj = assDtassuj;
	}
	
	
	public Long getIu() {
		return iu;
	}
	public void setIu(Long iu) {
		this.iu = iu;
	}
	
	
	public Short getRegCod() {
		return regCod;
	}
	public void setRegCod(Short regCod) {
		this.regCod = regCod;
	}
	
	
	public Short getAge() {
		age = (short)Period.between(this.getBenDtnais().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
		return age;
	}
	public void setAge(Short age) {
		this.age = age;
	}
	
	
	public Short getTypeRegime() {
		return typeRegime;
	}
	public void setTypeRegime(Short typeRegime) {
		this.typeRegime = typeRegime;
	}
	
	
	public Short getCategorieTns() {
		return categorieTns;
	}
	public void setCategorieTns(Short categorieTns) {
		this.categorieTns = categorieTns;
	}
	
	
	public Short getSituation() {
		return situation;
	}
	public void setSituation(Short situation) {
		this.situation = situation;
	}
	

}
