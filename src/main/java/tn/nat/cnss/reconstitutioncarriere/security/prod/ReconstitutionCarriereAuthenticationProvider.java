package tn.nat.cnss.reconstitutioncarriere.security.prod;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import local.cnss.security.client.CnssUserDetails;
import local.cnss.security.service.TokenAuthenticationProvider;


@Component
public class ReconstitutionCarriereAuthenticationProvider extends TokenAuthenticationProvider{
	
	@Autowired
	ProdSecurityTools devSecurityTools;
	
	
	
	
	public ReconstitutionCarriereAuthenticationProvider() {
		this.applicationId = ProdSecurityTools.applicationId;		
		this.secretKey = ProdSecurityTools.secretKey;
	}
	
	
	@SuppressWarnings("serial")
	@Override
	protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
		
		CnssUserDetails cnssUserDetails = (CnssUserDetails)super.retrieveUser(userName, usernamePasswordAuthenticationToken);
			
		
		//System.out.println("prod => User authenticated successfully");
		
		
		
		try {
			AdaptedProdUserDetails adaptedProdUserDetails =					
							devSecurityTools.adaptAuthenticatedProdUserToApllication(
									new AdaptedProdUserDetails(
											cnssUserDetails.getConnectionDetails(), 
											cnssUserDetails.getToken(), 
											cnssUserDetails.getApplicationId()
									), 
									((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest()
							) 
					;
			
			if(adaptedProdUserDetails.getBelongsTo() == null) {
				throw new AuthenticationException("Could not authenticate user") {};
			}
			return adaptedProdUserDetails;
		} catch (UnknownHostException e) {
			throw new AuthenticationException("Could not authenticate user") {};
		}catch (IllegalStateException e) {
			throw new AuthenticationException("Could not authenticate user") {};
		}
		
	}
	
}