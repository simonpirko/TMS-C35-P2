package by.tms.tmsc35p2.filter;

import by.tms.tmsc35p2.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class AuthRedirectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestURI = httpRequest.getRequestURI();

        boolean isAuthenticated = session != null && session.getAttribute("user") != null;

        if (isAuthenticated && (requestURI.equals("/auth/login") || requestURI.equals("/auth/register"))) {

            httpResponse.sendRedirect("/");
            return;
        }
        

        chain.doFilter(request, response);
    }
}
