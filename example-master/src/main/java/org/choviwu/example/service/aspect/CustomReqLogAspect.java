package org.choviwu.example.service.aspect;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.choviwu.example.common.util.JsonUtils;
import org.springframework.stereotype.Component;

/**
 * Created by ChoviWu on 2018/04/12
 * Description:用户请求AOP切面
 */
@Component
@Aspect
public class CustomReqLogAspect {

    @Pointcut(value = "@annotation(io.swagger.annotations.ApiImplicitParams)")
    public void pointCut(){}

    /**
     * springAOP 记录用户输入的参数信息
     * @param joinPoint
     * @param params
     * @return
     * @throws Throwable
     */
    @Around(value = "pointCut()&&@annotation(params)")
    public Object around(ProceedingJoinPoint joinPoint, ApiImplicitParams params) throws Throwable {
        ApiImplicitParam []apiImplicitParams = params.value();
        for (ApiImplicitParam param : apiImplicitParams){
            System.out.println(JsonUtils.toJson(param.value()+param.name()));
        }
        //业务
        return joinPoint.proceed();
    }
}
