package com.jd.test.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yantingjun on 2015/5/14.
 */
public class RegexUtil {
    public static String findContent(String content,String regex,int group){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            return matcher.group(group);
        }
        return "";
    }
}
