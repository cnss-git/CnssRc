package tn.nat.cnss.reconstitutioncarriere.controller;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import tn.nat.cnss.reconstitutioncarriere.security.dev.DevUserDetails;
import tn.nat.cnss.reconstitutioncarriere.security.dev.Utilisateur;
import tn.nat.cnss.reconstitutioncarriere.security.prod.AdaptedProdUserDetails;
import tn.nat.cnss.reconstitutioncarriere.security.prod.Structure;
import tn.nat.cnss.reconstitutioncarriere.security.prod.StructureRepository;

@ManagedBean
@SessionScoped
public class SessionBean implements Serializable{
	
	private static final long serialVersionUID = 3835416126235456397L;
	
	//private static ResourceBundle bundleMsgs;
    private static ResourceBundle applicationProperties;
   
    private String mode;
    
    private String userMatricule;
    private String userName;
    private Structure userBelongsTo;
    private Structure userConnectedFrom;
    private Structure userConnectedAs;
    
    private Short structureToSwitchTo;
    
    @ManagedProperty("#{structureRepository}")
    private StructureRepository structureRepository;

    //------------------------------------------------------------------------------------------------------------------------------------
    @PostConstruct
    public void init() {
    	FacesContext context 		= FacesContext.getCurrentInstance();
        //bundleMsgs 					= context.getApplication().getResourceBundle(context,"messages");
        applicationProperties 		= context.getApplication().getResourceBundle(context,"application");
        mode = applicationProperties.getString("env");
        
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (mode.compareTo("dev") == 0) {
			Utilisateur devUser = ((DevUserDetails)authentication.getPrincipal()).getDevUser();
			userMatricule = devUser.getMatricule();
			userName = devUser.getNiceName();
			userBelongsTo = devUser.getBelongsTo();
			userConnectedFrom = devUser.getConnectedFrom();
		}else {
			AdaptedProdUserDetails prodUser = (AdaptedProdUserDetails)authentication.getPrincipal();
			userMatricule = prodUser.getConnectionDetails().getMatricule().toString();
			userName = prodUser.getUsername();
			userBelongsTo = prodUser.getBelongsTo();
			userConnectedFrom = prodUser.getConnectedFrom();
		}
		userConnectedAs = userBelongsTo;
		structureToSwitchTo = userConnectedAs.getBurCod();
    }
    //------------------------------------------------------------------------------------------------------------------------------------
    

    /*
    public static void displayMessage(String tagLibParent, FacesMessage.Severity severity, String keyTitle , String keyMsg){    	
        FacesContext.getCurrentInstance().addMessage(tagLibParent, new FacesMessage(severity, bundleMsgs.getString(keyTitle), bundleMsgs.getString(keyMsg)));
    }
    


    public static void displayErrors(Map<String, String> errors){
    	FacesContext   context         		= FacesContext.getCurrentInstance();
    	ResourceBundle bundleExceptions 	= context.getApplication().getResourceBundle(context,"exceptions");
    	ResourceBundle bundleLanguage     	= context.getApplication().getResourceBundle(context,"labels");

		for (Map.Entry<String, String> error : errors.entrySet()){
		    String itemError     = error.getKey();
		    String messageError    = error.getValue();
		    String convertedArgs[]     = new String[1];
		    convertedArgs[0]        = bundleLanguage.getString(itemError);
		    String errorDetails = MessageFormat.format(bundleExceptions.getString(messageError), (Object[])convertedArgs);
		    context.addMessage("msgs",new FacesMessage(FacesMessage.SEVERITY_ERROR,  bundleLanguage.getString(itemError), errorDetails));
		}
    }
    */


    //------------------------------------------------------------------------------------------------------------------------------------
    public String switchStructure() {
    	//System.out.println(structureToSwitchTo);
    	setUserConnectedAs(structureRepository.findById((structureToSwitchTo)).orElse(userBelongsTo));
    	//System.out.println(userConnectedAs.getBurIntit());
    	return "welcome";
    }
    //------------------------------------------------------------------------------------------------------------------------------------

	


	//------------------------------------------------------------------------------------------------------------------------------------
    public String getUserMatricule() {		
		return userMatricule;
	}
	public void setUserMatricule(String userMatricule) {
		this.userMatricule = userMatricule;
	}
    //----
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	//----
	public Structure getUserBelongsTo() {
		return userBelongsTo;
	}
	public void setUserBelongsTo(Structure userBelongsTo) {
		this.userBelongsTo = userBelongsTo;
	}
	//----
	public Structure getUserConnectedFrom() {
		return userConnectedFrom;
	}
	public void setUserConnectedFrom(Structure userConnectedFrom) {
		this.userConnectedFrom = userConnectedFrom;
	}
	//----
	public Structure getUserConnectedAs() {
		return userConnectedAs;
	}
	public void setUserConnectedAs(Structure userConnectedAs) {
		this.userConnectedAs = userConnectedAs;
	}
	//----
	public Short getStructureToSwitchTo() {
		return structureToSwitchTo;
	}
	public void setStructureToSwitchTo(Short structureToSwitchTo) {
		this.structureToSwitchTo = structureToSwitchTo;
	}
	//----
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	//----
	public StructureRepository getStructureRepository() {
		return structureRepository;
	}
	public void setStructureRepository(StructureRepository structureRepository) {
		this.structureRepository = structureRepository;
	}
	//------------------------------------------------------------------------------------------------------------------------------------
	

}
