package com.jd.test.util;

/**
 * Created by yantingjun on 2015/4/29.
 */
public class StringUtils {
    public static boolean isBlank(String str){
        return str==null||str.trim().length()==0;
    }
    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }
}
