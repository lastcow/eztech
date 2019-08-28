package me.chen.eztech.filter;

import me.chen.eztech.model.User;
import me.chen.eztech.service.MD5Util;
import me.chen.eztech.service.UserService;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Order(1)
public class GravatarFilter implements Filter {

    private UserService userService;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        Principal principal = req.getUserPrincipal();

        // Wire
        if(userService == null){
            ServletContext servletContext = req.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userService = webApplicationContext.getBean(UserService.class);
        }
        // Get user
        Optional<User> user = userService.getUserByUsername(principal.getName());
        if(user.isPresent()){
            String email = user.get().getEmail();
            String emailHash = MD5Util.md5Hex(email);

            res.setHeader("avatarhash", emailHash);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
