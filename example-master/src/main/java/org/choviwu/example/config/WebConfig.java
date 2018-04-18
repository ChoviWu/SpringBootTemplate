package org.choviwu.example.config;

import org.choviwu.example.common.web.ExceptionInterceptor;
import org.choviwu.example.common.web.ServletContextListener;
import org.choviwu.example.common.web.SessionFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChoviWu on 2018/04/10
 * Description: 视图层配置  拦截器 过滤器等...
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.mvc.interceptor.list}")
    private String[] list;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        super.configureAsyncSupport(configurer);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(new ExceptionInterceptor()).excludePathPatterns(list);
    }

    /**
     * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
     *  swagger资源配置
     *  <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
     *  <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("/webjars*")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    /**
     * 过滤器
     * @return
     */
    @Bean(value = "registrationBean")
    public FilterRegistrationBean sessonFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SessionFilter());
        registrationBean.setName("SessionFilter");
        registrationBean.setEnabled(true);
        List<String> patterns = new ArrayList<>();
        patterns.add("/*");
        registrationBean.setUrlPatterns(patterns);
        return registrationBean;
    }

    /**
     * 监听器
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean registrationBean(){
        ServletListenerRegistrationBean listenerRegistrationBean = new ServletListenerRegistrationBean();
        listenerRegistrationBean.setListener(new ServletContextListener());
        return listenerRegistrationBean;
    }

    /**
     * 文件上传
     * @return
     */
    @Bean
    public MultipartConfigElement multipart(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setFileSizeThreshold("10MB");
        factory.setMaxFileSize("15MB");
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }


}
