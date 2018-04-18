package org.choviwu.example.common.web;

import org.choviwu.example.common.util.IpUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * ChoviWu Created By 2018年4月16日13:29:01
 */
@WebFilter("sessionFilter")
public class SessionFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest)req;
    HttpServletResponse response = (HttpServletResponse)res;
    addHeader(response);

    HttpSession session = request.getSession();
    Object userIdObj=session.getAttribute("userId");
    String path=request.getServletPath();
    //过滤链接
    String[] noNeedLoginPaths={"/user/login","/index","/favicon","/user/lottery/isLogin"};
    //需登录的
    String[] needLoginPaths={"/user","/account","/web","/invest","/forgetPassword"};

    boolean noNeedLogin=isMatch(path,noNeedLoginPaths);
    if(noNeedLogin){
      chain.doFilter(req,res);
    }else{
      boolean needLogin=isMatch(path,needLoginPaths);
      if(needLogin&&userIdObj==null){
        String queryString=request.getQueryString();
        if(StringUtils.isEmpty(queryString)){
          session.setAttribute("callback",path);
        }else{
          session.setAttribute("callback",path+"?"+queryString);
        }
        request.getRequestDispatcher("/user/login").forward(request, response);
      }else{
        chain.doFilter(req,res);
      }
    }
  }
  public void init(FilterConfig filterConfig) {}

  public void destroy() {}

  private boolean isMatch(String path, String[] paths){
    boolean isMatch = false;
    for(String p:paths){
      if(path.startsWith(p)){
        isMatch = true;
        break;
      }
    }
    return isMatch;
  }
  private void addHeader(HttpServletResponse response){
    response.addHeader("Access-Control-Allow-Methods","GET,POST,DELETE,OPTION,OPTIONS,PUT");
    response.addHeader("Access-Control-Allow-Headers","Content-Type,authorization,x-requested-with");
    response.addHeader("Access-Control-Max-Age","3600");
    response.addHeader("Access-Control-Allow-Credentials","true");
    //TOKEN 跨域
    response.addHeader("Access-Controll-token", UUID.randomUUID().toString());
  }

}