package app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.PreDestroy;
import java.sql.SQLException;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "app")
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver getViewResolver(){
            InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
            viewResolver.setPrefix("/WEB-INF/view/");
            return viewResolver;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(){
        //jdbc:mysql://localhost:3306/socialnetwork
        String connString = "jdbc:mysql://localhost:3306/socialnetwork";
        String username = "root";
        String password = "";
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(connString);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate;
    }


    @Bean
    public MultipartResolver getMultiPartResolver(){
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/server-resources/**").addResourceLocations("/serverResources/");
        registry.addResourceHandler("/user-resources/**").addResourceLocations("/userResources/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE");

    }

    @Value("http://localhost:8080/Feelbook")
    private String api;
    @Value("/Feelbook")
    private String root;
    @Bean
    public String getApi(){
        return api;
    }
    @Bean
    public String getRoot(){
        return root;
    }
}
