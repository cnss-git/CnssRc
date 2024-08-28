package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

@ManagedBean(name = "reportSalairesEstimatifsNonDeclaresController")
@ViewScoped
public class ReportSalairesEstimatifsNonDeclaresController extends AbstractReportBean implements Serializable{

	private static final long serialVersionUID = -3261025212640094077L;
	
	
	//--------------------------------------------------------------------------
	private Integer trimestre;
	private Integer annee;
	private String reportPdfFileUrl;
	//--------------------------------------------------------------------------
	
	
	
	
	//--------------------------------------------------------------------------
	public void edit() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_trim", trimestre);
		params.put("P_annee", annee);
		reportPdfFileUrl = super.prepareReport("SalairesEstimatifsNonDeclares", params);
		PrimeFaces.current().executeScript("PF('wReportDialog').show();");
	}
	//--------------------------------------------------------------------------
	
	
	
	
	//--------------------------------------------------------------------------
	public Integer getTrimestre() {
		return trimestre;
	}
	public void setTrimestre(Integer trimestre) {
		this.trimestre = trimestre;
	}
	//---
	public Integer getAnnee() {
		return annee;
	}
	public void setAnnee(Integer annee) {
		this.annee = annee;
	}
	//---
	public String getReportPdfFileUrl() {
		return reportPdfFileUrl;
	}
	public void setReportPdfFileUrl(String reportPdfFileUrl) {
		this.reportPdfFileUrl = reportPdfFileUrl;
	}
	//--------------------------------------------------------------------------


}
