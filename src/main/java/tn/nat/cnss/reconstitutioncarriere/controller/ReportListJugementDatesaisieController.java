package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

@ManagedBean(name = "reportListJugementDatesaisieController")
@ViewScoped
public class ReportListJugementDatesaisieController extends AbstractReportBean implements Serializable{

	private static final long serialVersionUID = -3261025212640094077L;
	
	
	//--------------------------------------------------------------------------
	private Date datesaisie;
	private String reportPdfFileUrl;
	//--------------------------------------------------------------------------
	
	
	
	
	//--------------------------------------------------------------------------
	public void edit() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_Date_saisie", datesaisie);
		reportPdfFileUrl = super.prepareReport("Jugement", params);
		PrimeFaces.current().executeScript("PF('wReportDialog').show();");
	}
	//--------------------------------------------------------------------------
	
	
	
	
	
	//--------------------------------------------------------------------------
	public Date getDatesaisie() {
		return datesaisie;
	}
	public void setDatesaisie(Date datesaisie) {
		this.datesaisie = datesaisie;
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
