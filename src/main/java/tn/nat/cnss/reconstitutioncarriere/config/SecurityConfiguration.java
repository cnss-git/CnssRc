package tn.nat.cnss.reconstitutioncarriere.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import local.cnss.security.service.CnssAuthenticationSuccessHandler;
import local.cnss.security.service.TokenAuthenticationFilter;
import tn.nat.cnss.reconstitutioncarriere.security.dev.DevAuthenticationSuccessHandler;
import tn.nat.cnss.reconstitutioncarriere.security.dev.DevUserDetailsService;
import tn.nat.cnss.reconstitutioncarriere.security.prod.ReconstitutionCarriereAuthenticationProvider;


@Configuration
@PropertySource("classpath:tn/nat/cnss/reconstitutioncarriere/properties/application.properties")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	
	private final static String PROTECTED_URLS = "/**";
	
	//AuthenticationProvider provider;
	
	@Autowired
	Environment env;
	
	@Autowired
	ReconstitutionCarriereAuthenticationProvider reconstitutionCarriereAuthenticationProvider;
	
	/*
	@Autowired
	@Qualifier("devUserDetailsService")
	UserDetailsService devUserDetailsService;
	@Autowired
	*/


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		if(env.getProperty("env").compareTo("prod")==0) {
			auth.authenticationProvider(reconstitutionCarriereAuthenticationProvider);
		}else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			auth.userDetailsService(devUserDetailsService()).passwordEncoder(passwordEncoder);
			//System.out.println(passwordEncoder.encode("75366"));
		}
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		if(env.getProperty("env").compareTo("prod") == 0) {
			http
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.exceptionHandling()
					.and()
				.addFilterBefore(prodAuthenticationFilter(), AnonymousAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers("/pages/dg.xhtml").hasRole("DG")
		        	.antMatchers("/pages/rachat.xhtml").hasRole("RACHAT")
		        	.antMatchers("/pages/salaire_estimatif_demande_retraite.xhtml").hasRole("SALAIRE_ESTIMATIF")
		        	.antMatchers("/pages/transfert_cnrps.xhtml").hasRole("TRANSFERT_CNRPS")
		        	.antMatchers("/pages/transfert_matricule.xhtml").hasRole("TRANSFERT_MATRICULE")
		        	.antMatchers("/pages/jugement.xhtml").hasRole("JUGEMENT")			
					.antMatchers("/pages/welcome.xhtml").authenticated()
					.anyRequest().authenticated()
					.and()				
				.csrf()
					.disable()
				.formLogin()
					.disable()
				.httpBasic()
					.disable()
				.logout().logoutUrl("/perform_logout").logoutSuccessUrl("http://portal.cnss.local/")
					//.disable()
					//.deleteCookies("JSESSIONID")
				/*.and()*///.headers().frameOptions().disable()
				.and().exceptionHandling().accessDeniedPage("/pages/access_denied.html");
		}else{
			http
        	.csrf().disable()
        	.authorizeRequests()
        	.antMatchers("/pages/dg.xhtml").hasRole("DG")
        	.antMatchers("/pages/rachat.xhtml").hasRole("RACHAT")
        	.antMatchers("/pages/salaire_estimatif_demande_retraite.xhtml").hasRole("SALAIRE_ESTIMATIF")
        	.antMatchers("/pages/transfert_cnrps.xhtml").hasRole("TRANSFERT_CNRPS")
        	.antMatchers("/pages/transfert_matricule.xhtml").hasRole("TRANSFERT_MATRICULE")
        	.antMatchers("/pages/jugement.xhtml").hasRole("JUGEMENT")
        	//.antMatchers("/pages/enregistrer_carte_ptt.xhtml").denyAll()
        	.antMatchers("/pages/welcome.xhtml").authenticated()
        	.antMatchers("/pages/authentification.xhtml").permitAll()
        	.antMatchers("/login").permitAll()
        	.anyRequest().authenticated()
        	.and()
        	.formLogin()
        	.loginPage("/pages/authentification.xhtml").permitAll()
        	.loginProcessingUrl("/perform_login")
        	//.defaultSuccessUrl("/pages/welcome.xhtml")
        	.successHandler(devAuthenticationSuccessHandler())
        	.and()
        	.logout()
        	.logoutUrl("/perform_logout")        	
        	.deleteCookies("JSESSIONID")
        	.and().exceptionHandling().accessDeniedPage("/pages/access_denied.html");
		}
	}

	@Override
	public void configure(WebSecurity web) {		
		web.ignoring().antMatchers("/javax.faces.resource/**","/resources/**");
		web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	}

	@Bean
	TokenAuthenticationFilter prodAuthenticationFilter() throws Exception {
		final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(new AntPathRequestMatcher(SecurityConfiguration.PROTECTED_URLS));
		filter.setAuthenticationManager(authenticationManager());
		//filter.setAuthenticationSuccessHandler(new CnssAuthenticationSuccessHandler() {});
		//filter.setAuthenticationSuccessHandler(devAuthenticationSuccessHandler());
		return filter;
	}
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();	    
	    firewall.setAllowSemicolon(true);
	    return firewall;
	}
	
	//-------------------------------------------------------------
	@Bean
	public UserDetailsService devUserDetailsService() {
		return new DevUserDetailsService();
	}
	@Bean
	public AuthenticationSuccessHandler devAuthenticationSuccessHandler() {
		return new DevAuthenticationSuccessHandler();
	}

}
