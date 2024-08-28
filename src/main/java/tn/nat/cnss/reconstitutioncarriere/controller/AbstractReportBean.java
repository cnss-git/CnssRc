package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public abstract class AbstractReportBean extends SpringBeanAutowiringSupport{
	
	Connection connection;
	private final String REPORTS_SOURCEPATH_AND_RELATIVEURL = "reports";
	private final String REPORTS_PATH = "tn/nat/cnss/reconstitutioncarriere/"+REPORTS_SOURCEPATH_AND_RELATIVEURL+"/";
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;


	//----------------------------------------------
	public void doEdit(String reportName, Map<String, Object> reportParameters){
		
		// Accés au fichier sur le serveur
		
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(REPORTS_PATH+reportName+".jrxml");
		
		//File reportFile = new File(getClass().getClassLoader().getResource(REPORTS_PATH+reportName+".jrxml").getFile());

		// Destination pour le téléchargement du rapport sur le disque
		String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		//System.out.println(realPath);

		File reportsPath = new File(realPath+"/"+REPORTS_SOURCEPATH_AND_RELATIVEURL);
		//voucherReportsPath.mkdirs();
		String destinationFile = reportsPath+"/"+reportName+"_"+UUID.randomUUID()+"_"+".pdf";
		
		System.out.println(destinationFile);

		// Préparation, compilation et export du fichier au format PDF
		try {			
			// Chargement et compilation du rapport
			JasperDesign jasperDesign = JRXmlLoader.load(input);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			Session session = (Session) entityManagerFactory.createEntityManager().getDelegate();
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {

						// Exécution du rapport avec les paramètres et la connection BDD
						JasperPrint jasperPrint;
						try {
							jasperPrint = JasperFillManager.fillReport(jasperReport,reportParameters, connection);
							JasperExportManager.exportReportToPdfFile(jasperPrint,destinationFile);
						} catch (JRException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
			});
						
		} catch (JRException e) {
			e.printStackTrace();
		} finally{
			try {
				if(connection != null) 
					connection.close();
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
		
		// Appel méthode qui permet de télécharger le rapport.
		//downloadPDF(destinationFile);
		
	}
	//----------------------------------------------
	
	
	
	
	//----------------------------------------------
	public String prepareReport(String reportName, Map<String, Object> reportParameters){
		
		// Accés au fichier sur le serveur
		
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(REPORTS_PATH+reportName+".jrxml");
		
		//File reportFile = new File(getClass().getClassLoader().getResource(REPORTS_PATH+reportName+".jrxml").getFile());

		// Destination pour le téléchargement du rapport sur le disque
		String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
		//System.out.println(realPath);

		File reportsPath = new File(realPath+"/"+REPORTS_SOURCEPATH_AND_RELATIVEURL);
		//voucherReportsPath.mkdirs();
		String randomReportName = reportName+"_"+UUID.randomUUID()+"_.pdf";
		String destinationFile = reportsPath+"/"+randomReportName;
		
		System.out.println(destinationFile);

		// Préparation, compilation et export du fichier au format PDF
		try {			
			// Chargement et compilation du rapport
			JasperDesign jasperDesign = JRXmlLoader.load(input);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			Session session = (Session) entityManagerFactory.createEntityManager().getDelegate();
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {

						// Exécution du rapport avec les paramètres et la connection BDD
						JasperPrint jasperPrint;
						try {
							jasperPrint = JasperFillManager.fillReport(jasperReport,reportParameters, connection);
							JasperExportManager.exportReportToPdfFile(jasperPrint,destinationFile);
						} catch (JRException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
			});
						
		} catch (JRException e) {
			e.printStackTrace();
		} finally{
			try {
				if(connection != null) 
					connection.close();
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
		
		String reportUrl = /*FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+*/"/"+REPORTS_SOURCEPATH_AND_RELATIVEURL+"/"+randomReportName;
		
		return reportUrl;
		
		
		// Appel méthode qui permet de télécharger le rapport.
		//downloadPDF(destinationFile);
		
	}
	//----------------------------------------------
	
	
		
		

	//----------------------------------------------
	public void downloadPDF(String filename)  {
		
		// Préparation de la réponse (Objet Response).
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		//	Préparation du fichier
		File file = new File(filename);
		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		// écriture du fichier dans la réponse
		try {
			// Ouverture du flux
			input = new BufferedInputStream(new FileInputStream(file), 10240);

			// Initialisation de la réponse
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
			output = new BufferedOutputStream(response.getOutputStream(), 10240);

			// écriture du flux dans la réponse
			byte[] buffer = new byte[10240];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			// Finalisation de la tâche.
			output.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		finally {
			// Fermeture du flux
			try {
				if(output != null)
					output.close();
				if(input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		// Envoie de la réponse
		facesContext.responseComplete();
		
	}
	//----------------------------------------------
	
	

	


}

