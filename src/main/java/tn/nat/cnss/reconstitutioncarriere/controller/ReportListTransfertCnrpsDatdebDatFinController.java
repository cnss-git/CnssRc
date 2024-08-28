package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.PrimeFaces;

@ManagedBean(name = "reportListTransfertCnrpsDatdebDatFinController")
@ViewScoped
public class ReportListTransfertCnrpsDatdebDatFinController extends AbstractReportBean implements Serializable{

	private static final long serialVersionUID = -3261025212640094077L;
	
	
	//--------------------------------------------------------------------------
	private Date dtDeb;
	private Date dtFin;
	private String reportPdfFileUrl;
	//--------------------------------------------------------------------------
	
	
	
	
	//--------------------------------------------------------------------------
	public void edit() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_dateDeb", dtDeb);
		params.put("P_dateFin", dtFin);
		reportPdfFileUrl = super.prepareReport("ListeDesTransfertsCnrps", params);
		PrimeFaces.current().executeScript("PF('wReportDialog').show();");
	}
	//--------------------------------------------------------------------------
	
	
	
	
	
	//--------------------------------------------------------------------------
	public Date getDtDeb() {
		return dtDeb;
	}
	public void setDtDeb(Date dtDeb) {
		this.dtDeb = dtDeb;
	}
	//---
	public Date getDtFin() {
		return dtFin;
	}
	public void setDtFin(Date dtFin) {
		this.dtFin = dtFin;
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
