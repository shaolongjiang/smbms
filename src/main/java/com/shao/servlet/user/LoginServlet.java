package com.shao.servlet.user;

import com.shao.pojo.User;
import com.shao.service.user.UserService;
import com.shao.service.user.UserServiceImpl;
import com.shao.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet---- start.");
        String userCode=req.getParameter("userCode");
        String userPassword=req.getParameter("userPassword");
        //和数据库中的数据对比
        UserService userService=new UserServiceImpl();
        User user=userService.login(userCode,userPassword);
        if(user!=null){
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            resp.sendRedirect(req.getContextPath()+"/jsp/frame.jsp");
        }else{//没有查到
            req.setAttribute("error","用户名或密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
