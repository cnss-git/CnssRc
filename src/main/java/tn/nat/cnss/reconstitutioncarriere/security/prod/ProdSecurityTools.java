package tn.nat.cnss.reconstitutioncarriere.security.prod;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.nat.cnss.reconstitutioncarriere.security.tools.SecurityTools;




@Component
public class ProdSecurityTools {
	
	public static int applicationId = 104;
	public static String secretKey = "KLY7LWUdNRt0wWI93u1gmqjKH6IgAuCS";
	
	
	
	@Autowired
	private SecurityTools securityTools;
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;


	public AdaptedProdUserDetails adaptAuthenticatedProdUserToApllication(AdaptedProdUserDetails adaptedProdUserDetails, HttpServletRequest request) throws UnknownHostException {
		
		adaptedProdUserDetails.setConnectedFrom(securityTools.decodeConnectedFrom(request));
		//adaptedProdUserDetails.setBelongsTo(structureRepository.findById(adaptedProdUserDetails.getConnectionDetails().getBureauId().shortValue()).orElse(null));
		Utilisateur connectedProdUtilisateur = utilisateurRepository.findById(adaptedProdUserDetails.getConnectionDetails().getMatricule()).orElse(null);
		if(connectedProdUtilisateur != null) {
			adaptedProdUserDetails.setBelongsTo(connectedProdUtilisateur.getBelongsTo());
		}else {
			adaptedProdUserDetails.setBelongsTo(null);
		}
		
		//@Mock Charger des rôles supllémentaires dynamiquement selon les données de connexion (url de connexion, code structure, .....)
		int newRolesLength =  adaptedProdUserDetails.getConnectionDetails().getRoles().length+1;
		String[] newRoles = new String[newRolesLength];
		for (int i=0 ; i<adaptedProdUserDetails.getConnectionDetails().getRoles().length ;i++) {
			newRoles[i] = adaptedProdUserDetails.getConnectionDetails().getRoles()[i];
		}
		newRoles[newRolesLength-1] = "ROLE_SPECIAL";
		adaptedProdUserDetails.getConnectionDetails().setRoles(newRoles);
		
		
		return adaptedProdUserDetails;
	}
	
	
	
	
	
	
	
	
	
	

}
