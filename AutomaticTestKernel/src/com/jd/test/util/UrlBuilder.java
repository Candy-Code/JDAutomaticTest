package com.jd.test.util;

import java.util.Map;

/**
 * Created by yantingjun on 2015/5/14.
 */
public class UrlBuilder {
    public static String build(String url,Map<String,Object> params){
        if(StringUtils.isBlank(url)){
            return "";
        }
        StringBuilder strBuilder = new StringBuilder(url);
        if(params!=null){
            strBuilder.append("?");
            for(Map.Entry<String,Object> entry : params.entrySet()){
                if(StringUtils.isNotBlank(entry.getKey())){
                    strBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
            }
            if(strBuilder.toString().endsWith("&")){
                strBuilder.substring(0,strBuilder.length()-1);
            }
        }

        return strBuilder.toString();
    }
}
