package com.cmcglobal.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.cmcglobal.entity.Users;
import com.cmcglobal.service.UsersService;
import com.cmcglobal.serviceImpl.JwtService;



public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
    public JwtAuthenticationTokenFilter() {
        super("/rest/**");
}

private final static String TOKEN_HEADER = "TOKEN";
@Autowired
private JwtService jwtService;

@Autowired
private UsersService usersService;

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String url = httpRequest.getRequestURI();
        String headerToken = httpRequest.getHeader(TOKEN_HEADER);
        String listRolePermissionAndMenu = httpRequest.getHeader("ListRolePermission");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpResponse.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        
        String[] urlOfRole = url.split("/");
        int len= urlOfRole.length;
        if (url.split("/")[1].contains("login")
                || url.split("/")[1].contains("registration")
                || url.split("/")[1].contains("forgotpass")
                || url.split("/")[1].contains("news")
                || url.split("/")[1].contains("slidebanner")
                || url.split("/")[1].contains("practice")
                || url.split("/")[1].contains("home")
                || url.split("/")[1].contains("active")
                || url.split("/")[1].contains("activeforgotpass")
                || url.split("/")[1].contains("customer")
                || url.split("/")[1].contains("active_account")
                || url.split("/")[1].contains("images")
                || url.split("/")[1].contains("resources")
                || url.split("/")[1].contains("changepassword")
                || url.split("/")[len -1].contains("downloadFileExcel")) {
                chain.doFilter(request, response);
        } 
        else {
                if (headerToken == null || !headerToken.startsWith("Token")) {
                	httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "The token is null");

                }
                String authToken = headerToken.substring(6);
                if (jwtService.validateTokenLogin(authToken)) {
                        String username = jwtService.getUsernameFromToken(authToken);

                        Users users = usersService.findByEmail(authToken);

                        if (users != null) {
                                boolean enabled = true;
                                boolean accountNonExpired = true;
                                boolean credentialsNonExpired = true;
                                boolean accountNonLocked = true;
                                UserDetails userDetails = new User(username, users.getPassword(), enabled, accountNonExpired,
                                        credentialsNonExpired, accountNonLocked,
                                        (Collection<? extends GrantedAuthority>) users.getAuthorities());
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                                getAuthenticationManager().authenticate(authentication);

                                Map<String, String> listPermissionAndMenuOfRole = new HashMap<>();
                                String role = jwtService.getRoleFromToken(authToken);
                                String[] listRole = role.split(",");
                                System.out.println(listRolePermissionAndMenu);
                                String[] listRolePerandMenu = listRolePermissionAndMenu.split(",");
                                int listRolelength = listRole.length;
                                int listRolePerAndMenulength = listRolePerandMenu.length;
                                for (int index = 0; index < listRolelength; index++) {
                                        for (int index1 = 0; index1 <= listRolePerAndMenulength - 4; index1 += 4) {
                                                String old = listPermissionAndMenuOfRole.get(listRole[index]);
                                                if (old == null)
                                                        old = "";
                                                if (listRolePerandMenu[index1].equals(listRole[index])) {
                                                        old += listRolePerandMenu[index1 + 2] + listRolePerandMenu[index1 + 3] + ",";
                                                }
                                                listPermissionAndMenuOfRole.put(listRole[index], old);
                                        }
                                }
                                for (int index = 0; index < listRole.length; index++) {
                                        String actionOfRole = listPermissionAndMenuOfRole.get(listRole[index]);
                                        if (actionOfRole.contains(urlOfRole[1])) ;
                                }

                                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
                        }
                }
                chain.doFilter(request, response);
        }

}

@Override
public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {
        // TODO Auto-generated method stub
        return null;
}
}
