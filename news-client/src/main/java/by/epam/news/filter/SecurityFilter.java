package by.epam.news.filter;

import by.epam.news.servlet.command.CommandName;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8")})
public class SecurityFilter implements Filter {
    private static final String COMMAND = "command";
    private static final String INDEX = "/index.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpServletRequest request = (HttpServletRequest)servletRequest;

        String command = request.getParameter(COMMAND);
        if (command != null) {
            try {
                CommandName commandName = CommandName.valueOf(command.toUpperCase());
                if (!commandName.getMethod().equalsIgnoreCase(request.getMethod())) {
                    response.sendRedirect(request.getContextPath() + INDEX);
                    return;
                }
            } catch (IllegalArgumentException e) {
                response.sendRedirect(request.getContextPath() + INDEX);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}