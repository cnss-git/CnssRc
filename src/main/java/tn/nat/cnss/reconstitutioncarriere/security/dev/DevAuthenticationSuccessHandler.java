package tn.nat.cnss.reconstitutioncarriere.security.dev;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class DevAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	
	@Autowired
	DevSecurityTools devSecurityTools;
	

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication
	) throws IOException, ServletException {
		
		//System.out.println("dev => User authenticated successfully");
		
		RedirectStrategy rs = new DefaultRedirectStrategy();
		
		
		Utilisateur devUser = ((DevUserDetails)authentication.getPrincipal()).getDevUser();
		
		try {
			devUser.setNiceName(devUser.getPrenom().concat(" ").concat(devUser.getNom()));
		}catch(NullPointerException e) {
			devUser.setNiceName("");
		}
		
		devSecurityTools.adaptAuthenticatedUserToApllication(
				((DevUserDetails)authentication.getPrincipal()).getDevUser(), 
				((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest()
		); 
		
		rs.sendRedirect(request, response, "/");
		
		
	}
	
	

}
