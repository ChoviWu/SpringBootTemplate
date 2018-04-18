package org.choviwu.example.common.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChoviWu on 2018/04/16
 * Description:
 */
public class StringUtils {

    /**
     * 校验对象空
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    /**
     * 校验字符是否为空
     * @param str
     * @return
     */
    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    /**
     * 校验字符串是否为空
     * @param str
     * @return
     */
    public static boolean hasLength(String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * 校验字符串是否为空 并校验字符串长度
     * @param str
     * @return
     */
    public static boolean hasText(String str) {
        return hasLength(str) && containsText(str);
    }


    private static boolean containsText(CharSequence str) {
        int strLen = str.length();

        for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成统一订单号  4位随机数
     * @return
     */
    public static String getOrderNo(){
        return Long.toString(new Date().getTime())+(int)(Math.random()*10000);
    }

    /**
     * 生成随机数字
     * @param length
     * @param isNumber
     * @return
     */
    public static String createRandomNumber(Integer length,Boolean isNumber){
        StringBuffer retStr = new StringBuffer();
        String strTable = isNumber ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr.append("");
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr.append(strTable.charAt(intR));
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr.toString();
    }

    /**
     * 判断响应是否正常
     * @param map
     * @return
     */
    public static boolean isSuccess(Map map){
        if(("success").equals(map.get("result"))){
            return true;
        }else
            return false;
    }

    /**
     * 判断响应是否正常
     * @param response
     * @return
     */
    public static boolean isSuccess(String response){
        Map map = new HashMap();
        map = JsonUtils.json2Map(response);
        if(("success").equals(map.get("result"))){
            return true;
        }else
            return false;
    }

    public static void main(String[] args) {
        System.out.println(createRandomNumber(10,true));
        System.out.println(getOrderNo());
    }
}
