package validator.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import iban.configuration.IBANValidatorDBConfiguration;

public class ValidatorInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext ctx =
				new AnnotationConfigWebApplicationContext();
		
		ctx.register(IBANValidatorConfiguration.class);
		ctx.register(IBANValidatorDBConfiguration.class);
		
		ctx.setServletContext(container);
		ctx.refresh();
		
		ServletRegistration.Dynamic servlet = 
				container.addServlet("deispatcher", new DispatcherServlet(ctx));
		
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}
}
