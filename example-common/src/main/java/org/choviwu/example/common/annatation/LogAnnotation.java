package org.choviwu.example.common.annatation;

import java.lang.annotation.*;

/**
 * Created by ChoviWu on 2018/04/12
 * Description:
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation {

    String value() default "";
}
