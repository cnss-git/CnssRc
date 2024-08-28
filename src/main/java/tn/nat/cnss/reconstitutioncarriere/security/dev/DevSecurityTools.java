package tn.nat.cnss.reconstitutioncarriere.security.dev;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tn.nat.cnss.reconstitutioncarriere.security.tools.SecurityTools;




@Component
public class DevSecurityTools {
	
	public static int applicationId = 51;
	public static String secretKey = "vpfuhpS3RYbVHdwMLeWBYKrLGRzSuenA";
	
	@Autowired
	private SecurityTools securityTools;
	
	
	public Utilisateur adaptAuthenticatedUserToApllication(Utilisateur devUser,  HttpServletRequest request) throws UnknownHostException {
		
		devUser.setConnectedFrom(securityTools.decodeConnectedFrom(request));
		
		
		//@Mock Charger des rôles supllémentaires dynamiquement selon les données de connexion (url de connexion, code structure, .....)
		ApplicationRole specialApplicationRole = new ApplicationRole();
		//ApplicationRoleId specialApplicationRoleId = new ApplicationRoleId();
		specialApplicationRole.setSpringSecurityRoleCode("ROLE_SPECIAL");
		devUser.getRolesInApplication().add(specialApplicationRole);
		
		
		return devUser;
		
	}
	

}
