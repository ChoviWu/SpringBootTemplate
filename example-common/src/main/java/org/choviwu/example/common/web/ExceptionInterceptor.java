package org.choviwu.example.common.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.choviwu.example.common.exception.CrudException;
import org.choviwu.example.common.util.IpUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * ChoviWu Created By 2018年4月16日13:29:01
 */
public class ExceptionInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        IpUtils.set(request);
        return true;
    }  
      

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }  
    //异常处理拦截器
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
    throws Exception {
        HashMap<String, String> msg = new HashMap<String, String>();
        if (ex != null) {
            msg.put("result", "fail");
            if(ex instanceof CrudException){
                CrudException exception = (CrudException) ex;
                msg.put("messageCode", exception.getCode());
                msg.put("messageText", ex.getMessage());
            }else{
                msg.put("messageCode", "sys.error");
                msg.put("messageText", "系统繁忙");
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(msg);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        }
    }
      
}  