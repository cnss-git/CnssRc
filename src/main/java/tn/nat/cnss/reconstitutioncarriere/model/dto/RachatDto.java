package tn.nat.cnss.reconstitutioncarriere.model.dto;

import java.io.Serializable;
import java.util.Date;

public class RachatDto implements Serializable{
	
	private static final long serialVersionUID = -6723581396508424270L;
	
	
	private AssureDto assure;
	private Date dateDemande;
	private Integer salaireTrim;
	private Short tauxRachat;
	private Short trimestreDebut;
	private Short anneeDebut;
	private Short trimestreFin;
	private Short anneeFin;
	private Short nombreDeTrimestres;
	private Integer totalCotisationRachat;
	private Integer cotisationTrimestriel;
	
	
	
	public AssureDto getAssure() {
		return assure;
	}
	public void setAssure(AssureDto assure) {
		this.assure = assure;
	}
	
	
	public Integer getSalaireTrim() {
		return salaireTrim;
	}
	public void setSalaireTrim(Integer salaireTrim) {
		this.salaireTrim = salaireTrim;
	}
	
	
	public Short getTauxRachat() {
		return tauxRachat;
	}
	public void setTauxRachat(Short tauxRachat) {
		this.tauxRachat = tauxRachat;
	}
	
	
	public Short getTrimestreDebut() {
		return trimestreDebut;
	}
	public void setTrimestreDebut(Short trimestreDebut) {
		this.trimestreDebut = trimestreDebut;
	}
	
	
	public Short getAnneeDebut() {
		return anneeDebut;
	}
	public void setAnneeDebut(Short anneeDebut) {
		this.anneeDebut = anneeDebut;
	}
	
	
	public Short getTrimestreFin() {
		return trimestreFin;
	}
	public void setTrimestreFin(Short trimestreFin) {
		this.trimestreFin = trimestreFin;
	}
	
	
	public Short getAnneeFin() {
		return anneeFin;
	}
	public void setAnneeFin(Short anneeFin) {
		this.anneeFin = anneeFin;
	}
	
	
	public Short getNombreDeTrimestres() {
		return nombreDeTrimestres;
	}
	public void setNombreDeTrimestres(Short nombreDeTrimestres) {
		this.nombreDeTrimestres = nombreDeTrimestres;
	}
	
	
	public Integer getTotalCotisationRachat() {
		return totalCotisationRachat;
	}
	public void setTotalCotisationRachat(Integer totalCotisationRachat) {
		this.totalCotisationRachat = totalCotisationRachat;
	}
	
	
	public Date getDateDemande() {
		return dateDemande;
	}
	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}
	
	
	public Integer getCotisationTrimestriel() {
		return cotisationTrimestriel;
	}
	public void setCotisationTrimestriel(Integer cotisationTrimestriel) {
		this.cotisationTrimestriel = cotisationTrimestriel;
	}
	
	

}
