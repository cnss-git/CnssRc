package tn.nat.cnss.reconstitutioncarriere.util;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class ApplicationUtil {
	
	/*
	public static String getMessage(String resourceName ,String cle) {
		return FacesContext
				.getCurrentInstance()
				.getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(),resourceName)
				.getString(cle);
	}
	*/
	
	public static final String MODE_EDIT = "edit"; 
	public static final String MODE_ADD = "add"; 
	
	
	public static void displayMessage(String tagLibParent, FacesMessage.Severity severity, String keyTitle , String keyMsg){
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundleMsgs = context.getApplication().getResourceBundle(context,"messages");
		FacesContext.getCurrentInstance().addMessage(tagLibParent, new FacesMessage(severity, bundleMsgs.getString(keyTitle), bundleMsgs.getString(keyMsg)));
	}
	
	public static void displayErrors(Map<String, String> errors){
	   	FacesContext 	context 		= FacesContext.getCurrentInstance();
	   	ResourceBundle bundleErrors 	= context.getApplication().getResourceBundle(context,"errors");
		ResourceBundle bundleLabels 	= context.getApplication().getResourceBundle(context,"labels");
		ResourceBundle bundleMessages	= context.getApplication().getResourceBundle(context,"messages");
		
		for (Map.Entry<String, String> error : errors.entrySet())
		{   
		    String itemError 	= error.getKey();
		    String messageError = error.getValue();
		    if (!error.getValue().contains(";;;")) {
		    	messageError = bundleErrors.getString(error.getValue());
		    }else {
		    	String[] s = error.getValue().split(";;;");
		    	messageError = s[0];
		    	String[] formatArguments = IntStream.range(1, s.length).mapToObj(i -> s[i]).toArray(String[]::new);
		    	try {
		    		messageError = String.format(bundleErrors.getString(messageError), (Object[])formatArguments);
		    	}catch (Exception e) {
					e.printStackTrace();
				}
		    }
		    
		    try {
		    	Integer.parseInt(itemError);
		    	context.addMessage("msgs",new FacesMessage(FacesMessage.SEVERITY_ERROR,  bundleMessages.getString("error"), messageError/*bundleErrors.getString(error.getValue())*/));
		    }catch(NumberFormatException e) {
		    	//String messageError	= error.getValue();
			    String convertedArgs[] 	= new String[1];
				convertedArgs[0]		= bundleLabels.getString(itemError);
				String errorDetails = MessageFormat.format(/*bundleErrors.getString(messageError)*/messageError, (Object[])convertedArgs);
				context.addMessage("msgs",new FacesMessage(FacesMessage.SEVERITY_ERROR,  bundleLabels.getString(itemError), errorDetails));
		    }
		}
	}
	
	
	
	public static void displayError(String errorKey, String clientId){
		clientId = clientId==null?"msgs":clientId;
	   	FacesContext 	context 		= FacesContext.getCurrentInstance();
	   	ResourceBundle bundleErrors 	= context.getApplication().getResourceBundle(context,"errors");
	   	ResourceBundle bundleMessages	= context.getApplication().getResourceBundle(context,"messages");
	   	context.addMessage(clientId,new FacesMessage(FacesMessage.SEVERITY_ERROR,  bundleMessages.getString("error"), bundleErrors.getString(errorKey)));
	}
	
	
	public static Date trimestreToDate(Short trimestre, Short annee) throws ParseException {
		String mois = "zz";
		switch (trimestre) {
		case 1:
			mois = "01";
			break;
		case 2:
			mois = "04";
			break;
		case 3:
			mois = "07";
			break;
		case 4:
			mois = "10";
			break;
		}
		String sDate1="01-"+mois+"-"+annee;  
	    return new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
	}
	
	
	public static int[] getDiffYearsSup(Date first, Date last) {
		
		int[] diff = new int[3];
		
		Calendar cFirst = Calendar.getInstance();
	    cFirst.setTime(first);
	    Calendar cLast = Calendar.getInstance();
	    cLast.setTime(last);
	    
	    diff[0] = cLast.get(Calendar.DATE) - cFirst.get(Calendar.DATE);
	    diff[1] = cLast.get(Calendar.MONTH) - cFirst.get(Calendar.MONTH);
	    diff[2] = cLast.get(Calendar.YEAR) - cFirst.get(Calendar.YEAR);

	    return diff;
	}

	public static void displayError(String errorKey, String labelKey, String... replacements) {
		FacesContext 	context 		= FacesContext.getCurrentInstance();
		ResourceBundle bundleLabels 	= context.getApplication().getResourceBundle(context,"labels");
		ResourceBundle bundleErrors 	= context.getApplication().getResourceBundle(context,"errors");
		ResourceBundle bundleMessages 	= context.getApplication().getResourceBundle(context,"messages");
		
		String[] replacementsWithLabel = new String[replacements.length+1];
		replacementsWithLabel[0] = bundleLabels.getString(labelKey);
		for(int i=1 ; i<=replacements.length ; i++) {
			replacementsWithLabel[i] = replacements[i-1];
		}
		
		String errorDetails = String.format(bundleErrors.getString(errorKey), (Object[])replacementsWithLabel);
		context.addMessage("msgs",new FacesMessage(FacesMessage.SEVERITY_ERROR,  bundleMessages.getString("error"), errorDetails));
	}

}
