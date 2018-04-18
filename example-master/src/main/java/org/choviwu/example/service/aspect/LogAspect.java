package org.choviwu.example.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.choviwu.example.common.annatation.LogAnnotation;
import org.choviwu.example.common.util.JsonUtils;
import org.springframework.stereotype.Component;

/**
 * Created by ChoviWu on 2018/04/12
 * Description:自定义注解日志记录AOP
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut(value = "@annotation(org.choviwu.example.common.annatation.LogAnnotation)")
    public void pointCut(){}

    /**
     * 环绕通知
     * @param joinpoint
     * @param logAnnotation
     * @return
     * @throws Throwable
     */
    @Around(value = "pointCut()&&@annotation(logAnnotation)")
    public Object aroundAdvice(ProceedingJoinPoint joinpoint, LogAnnotation logAnnotation) throws Throwable {
        String value = logAnnotation.value();
        System.out.println(value);
        //业务
        return joinpoint.proceed();
    }
    /**
     * 后置通知
     * @param joinPoint
     * @param logAnnotation
     */
    @After(value = "pointCut()&&@annotation(logAnnotation)")
    public void after(JoinPoint joinPoint,LogAnnotation logAnnotation){
        Object args = joinPoint.getArgs();//参数
        System.out.println(JsonUtils.toJson(args));
    }
    /**
     * 返回通知
     * @param result
     * @param logAnnotation
     */
    @AfterReturning(value = "pointCut()&&@annotation(logAnnotation))",returning = "result")
    public void afterReturning(Object result, LogAnnotation logAnnotation){
        //业务
        System.out.println(JsonUtils.toJson(result));
    }
}
