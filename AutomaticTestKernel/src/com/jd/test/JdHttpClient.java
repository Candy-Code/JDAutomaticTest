package com.jd.test;

import com.jd.test.util.JsonUtil;
import com.jd.test.util.JsonpParser;
import com.jd.test.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by yantingjun on 2015/5/14.
 */
public class JdHttpClient {
    Logger logger = Logger.getLogger(JdHttpClient.class);

    CloseableHttpClient httpClient = null;

    public JdHttpClient(){
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClient = httpClientBuilder.build();
    }
    public String getJson(String url){
        if(StringUtils.isBlank(url)){
            return null;
        }
        HttpGet httpGet = new HttpGet(url);
        HttpEntity responseEntity = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                responseEntity = response.getEntity();
                String json = EntityUtils.toString(responseEntity, "UTF-8");
                logger.debug(json);
                return json;
            }
        } catch (IOException e) {
            logger.error(e);
        }finally {
            try {
                EntityUtils.consume(responseEntity);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return null;
    }
    public  <T> T getJson(String url,Class<T> clazz){
        String json = getJson(url);
        if(StringUtils.isBlank(json)){
            return null;
        }
        return JsonUtil.fromJson(json,clazz);
    }
    public String postJson(String url,String requestJson){
        if(StringUtils.isBlank(url)){
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(requestJson,"UTF-8"));
        HttpEntity responseEntity = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                responseEntity = response.getEntity();
                String json = EntityUtils.toString(responseEntity, "UTF-8");
                logger.debug(json);
                return json;
            }
        } catch (IOException e) {
            logger.error(e);
        }finally {
            try {
                EntityUtils.consume(responseEntity);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return null;
    }
    public  <T> T postJson(String url,String requestJson,Class<T> clazz){
        String json = postJson(url, requestJson);
        if(StringUtils.isBlank(json)){
            return null;
        }
        return JsonUtil.fromJson(json,clazz);
    }

    public String getJsonp(String url, String callback) {
        if(url.contains("?")) {
            url = url + "&callback=" + callback;
        } else {
            url = url + "?callback=" + callback;
        }
        String json = JsonpParser.parseJson(this.getJson(url), callback);
        logger.debug(json);
        return json;
    }
    public <T> T getJsonpObject(String url, String callback,Class<T> clazz) {
        return JsonUtil.fromJson(getJsonp(url, callback),clazz);
    }
}
