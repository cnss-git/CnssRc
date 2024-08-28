package tn.nat.cnss.reconstitutioncarriere.security.dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//@Service("devUserDetailsService")
public class DevUserDetailsService implements UserDetailsService{
	
	
	@Autowired
	private DevUserRepository devUserRepository;
	
	@Autowired
	DevSecurityTools devSecurityTools;
	 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			Utilisateur devUser = devUserRepository.getUserByMatricule(username);
			
			if(devUser == null) {
				throw new UsernameNotFoundException("Could not find user");
			}
				
			return new DevUserDetails
					(
							devSecurityTools.adaptAuthenticatedUserToApllication(
									devUser, 
									((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest()
							) 
					);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	

}
