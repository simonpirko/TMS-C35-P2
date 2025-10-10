package by.tms.tmsc35p2;

import by.tms.tmsc35p2.configuration.WebConfiguration;
import by.tms.tmsc35p2.filter.AuthRedirectFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.Filter;

/**
 * @author simonpirko
 * @version 1.0
 * @since 28.09.2025
 */

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        
        AuthRedirectFilter authRedirectFilter = new AuthRedirectFilter();
        
        return new Filter[]{characterEncodingFilter, authRedirectFilter};
    }
}
