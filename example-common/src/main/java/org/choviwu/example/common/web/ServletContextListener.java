package org.choviwu.example.common.web;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * Created by ChoviWu on 2018/04/12
 * Description: 初始化
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext =  sce.getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//        AaMapper mapper = (AaMapper) applicationContext.getBean("AaMapper");
//
//        List<Aa> list= mapper.selectAll();
        //TODO
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
