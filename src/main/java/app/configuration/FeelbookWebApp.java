package app.configuration;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class FeelbookWebApp implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        ServletRegistration.Dynamic myCustomServlet = servletContext.addServlet("myCustomServlet", dispatcherServlet);
        myCustomServlet.setLoadOnStartup(1);
        myCustomServlet.addMapping("/");

        long maxSize = 10L*1024*1024*1024;
        myCustomServlet.setMultipartConfig(new MultipartConfigElement(null, maxSize, maxSize, 0));
    }
}
