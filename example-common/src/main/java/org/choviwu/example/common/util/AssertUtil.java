package org.choviwu.example.common.util;

import org.choviwu.example.common.exception.CrudException;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * Created by ChoviWu on 2018/02/22
 * Description:
 */
public class AssertUtil {

    /**
     * Null
     * @param obj
     * @param message
     */
    public static void isNullOrEmpty(Object obj,String message){
        if(Objects.isNull(obj))
            throw new CrudException(message);
    }

    public static void isFalse(Boolean flag,String message){
        if(!flag)
            throw new CrudException(message);
    }


    public static void isTrue(Object obj,Object object,String message){
        if(StringUtils.isEmpty(obj) || StringUtils.isEmpty(object))
            throw new RuntimeException("param_is_not_null");
        if(Objects.equals(obj,object))
            throw new CrudException(message);
    }

    /**
     * 取反
     * @param flag
     * @param message
     */
    public static void isTrue(Boolean flag,String message){

        if(!flag)
            throw new CrudException(message);
    }
}
