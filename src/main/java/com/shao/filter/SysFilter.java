package com.shao.filter;

import com.shao.util.Constants;
import  com.shao.pojo.User;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

 public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpServletResponse resp=(HttpServletResponse)response;
        User user= (User)req.getSession().getAttribute(Constants.USER_SESSION);
        if(user==null){
            resp.sendRedirect("/smbms/error.jsp");
        }else{
            chain.doFilter(request,response);
    }
    }

    public void destroy() {

    }
}
