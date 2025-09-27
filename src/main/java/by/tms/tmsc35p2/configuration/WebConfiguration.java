package by.tms.tmsc35p2.configuration;

import jakarta.servlet.annotation.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * @author simonpirko
 * @version 1.0
 * @since 28.09.2025
 */

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan("by.tms.tmsc35p2")
@PropertySource("classpath:application.properties")
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${thymeleaf.prefix}")
    private String prefix;

    @Value("${thymeleaf.suffix}")
    private String suffix;

    @Value("${thymeleaf.cacheable}")
    private Boolean cacheable;

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(cacheable);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }
}
