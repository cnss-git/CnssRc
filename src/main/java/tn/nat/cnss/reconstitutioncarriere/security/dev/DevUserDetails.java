package tn.nat.cnss.reconstitutioncarriere.security.dev;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DevUserDetails implements UserDetails{

	private static final long serialVersionUID = -631551453492685898L;
	
	private Utilisateur devUser;
	
	private List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
	
	public DevUserDetails(Utilisateur devUser) {
		for(ApplicationRole applicationRole : devUser.getRolesInApplication()) {
			authorities.add(new SimpleGrantedAuthority(applicationRole.getSpringSecurityRoleCode()));
		}
		this.devUser = devUser;
	}

	public Utilisateur getDevUser() {
		return devUser;
	}
	public void setDevUser(Utilisateur devUser) {
		this.devUser = devUser;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return devUser.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return devUser.getMatricule().toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	

}
