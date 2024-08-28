package tn.nat.cnss.reconstitutioncarriere;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.EnumSet;

public class AppInitializer implements WebApplicationInitializer {

	
	
	// gets invoked automatically when application starts up
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

	  	AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        //ctx.register(AppConfig.class);
	  	ctx.setConfigLocation("tn.nat.cnss.reconstitutioncarriere.config");

        ctx.setServletContext(servletContext);
        
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Production");
        servletContext.setInitParameter("javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
        //servletContext.setInitParameter("log4jConfiguration", "/WEB-INF/classes/log4j2.properties");
        

        Dynamic facesServlet = servletContext.addServlet("faces-servlet", new FacesServlet());
        facesServlet.addMapping("*.xhtml");
        facesServlet.addMapping("*.jsf");
        facesServlet.setLoadOnStartup(1);
        
        servletContext.addListener(new ContextLoaderListener(ctx));
        servletContext.addListener(new RequestContextListener());
        

        servletContext.setInitParameter("primefaces.THEME", "south-street");
        servletContext.setInitParameter("facelets.SKIP_COMMENTS", "true");
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString()); 
        
        FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        springSecurityFilterChain.addMappingForUrlPatterns(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST), true, "/*");
	  
	}

}