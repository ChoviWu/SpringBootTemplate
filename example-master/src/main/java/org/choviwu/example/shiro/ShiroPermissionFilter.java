package org.choviwu.example.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.choviwu.example.common.model.BasResource;
import org.choviwu.example.common.model.BasRole;
import org.choviwu.example.mapper.BasResourceMapper;
import org.choviwu.example.mapper.BasRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChoviWu on 2018/04/11
 * Description: 用户权限信息过滤
 */
@Component("permissionFilter")
public class ShiroPermissionFilter extends AccessControlFilter {

    @Autowired
    private BasResourceMapper resourceMapper;
    @Autowired
    private BasRoleMapper roleMapper;

    private static Logger logger = LoggerFactory.getLogger(ShiroPermissionFilter.class);

    /**
     * 校验所有权限
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue)   {
        //获取当前所登陆的对象
        Subject subject = getSubject(req, resp);
        //配置url所必须的角色 数组  可为空
        String[] rolesArray = (String[]) mappedValue;
        return this.matchRole(rolesArray,subject,req);
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //设置请求头
        setHeader(res,req);
        Subject subject = getSubject(request, response);
        try {
            //单一用户登录 同时多个用户则踢掉一个
            if (subject != null && subject.isAuthenticated()) {
                //强制退出
                getSubject(request, response).logout();
                //退出
                returnLogout(res);
                return false;
            }
        } catch (Exception e) {
            logger.error("==========踢人出问题了==========");
        }
        //未登录
        return false;
    }

    /**
     * 匹配资源url
     * @return
     */
    private boolean matchRole(String [] rolesArray,Subject subject,ServletRequest req) {
        //没有角色限制，有权限访问  anon
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        HttpServletRequest request = (HttpServletRequest) req;
        String requestUrl = request.getRequestURI();
        // 匹配所有角色 roles
        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) {
                BasRole role = roleMapper.getOneByRoleName(rolesArray[i]);
                if(StringUtils.isEmpty(role)) {
                    return false;
                }
                List<BasResource> list =  resourceMapper.getResourceListByRoleId(role.getId());
                for(BasResource resource : list){
                    if(requestUrl.equals(resource.getResourceUrl())){
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean pathsMatch(String pattern, String path) {
        return super.pathsMatch(pattern, path);
    }

    private void returnLogout(HttpServletResponse res) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map msg = new HashMap();
        msg.put("result", "fail");
        msg.put("messageCode", "sys.error");
        msg.put("messageText", "系统繁忙");
        String json = mapper.writeValueAsString(msg);
        res.setContentType("application/json;charset=UTF-8");
        res.setHeader("Pragma", "No-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", 0);
        PrintWriter out = res.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    private void setHeader(HttpServletResponse res,HttpServletRequest req){
        if(!StringUtils.isEmpty(req.getHeader("Origin"))){
            res.addHeader("Access-Control-Allow-Origin",req.getHeader("Origin"));
        }else{
            res.addHeader("Access-Control-Allow-Origin","*");
        }
        res.addHeader("Access-Control-Allow-Methods","GET,POST,DELETE,OPTION,OPTIONS,PUT");
        res.addHeader("Access-Control-Allow-Headers","Content-Type,authorization,x-requested-with");
        res.addHeader("Access-Control-Max-Age","3600");
        res.addHeader("Access-Control-Allow-Credentials","true");
    }
}
