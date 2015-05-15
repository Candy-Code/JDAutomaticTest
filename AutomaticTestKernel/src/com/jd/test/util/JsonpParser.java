package com.jd.test.util;

/**
 * Created by yantingjun on 2015/5/14.
 */
public class JsonpParser {
    public static  <T> T parse(String jsonp,String function,Class<T> clazz){
        return JsonUtil.fromJson(parseJson(jsonp,function),clazz);
    }
    public static  String parseJson(String jsonp,String function){
        return RegexUtil.findContent(jsonp, function + "\\((.*)\\)",1);
    }
}
