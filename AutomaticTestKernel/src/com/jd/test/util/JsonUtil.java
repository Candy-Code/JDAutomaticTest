package com.jd.test.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Json转换的工具类
 * @author bluefisher
 */
@SuppressWarnings( { "unchecked", "deprecation" })
public class JsonUtil {
	private final static Log LOG = LogFactory.getLog(JsonUtil.class);
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper(); // 新版 simleFactory 二进制，高性能

	/**
	 * 空参数构造器
	 */
	private JsonUtil() {
	}

	/**
	 * 静态块初始化
	 */
	static {
		//设置输出包含的属性
		OBJECT_MAPPER.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		OBJECT_MAPPER.configure(Feature.ALLOW_SINGLE_QUOTES,true);
		//设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		OBJECT_MAPPER.getDeserializationConfig().set(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		OBJECT_MAPPER.getSerializationConfig().setDateFormat(df);
		OBJECT_MAPPER.getDeserializationConfig().setDateFormat(df);
	}
	private static final ObjectMapper objMapper = new ObjectMapper();
	static{
		objMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		objMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
	}
	/**
	 * 如果JSON字符串为Null或"null"字符串,返回Null.
	 * 如果JSON字符串为"[]",返回空集合.
	 * @param jsonString
	 * @param clazz
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		try {
			return (T) OBJECT_MAPPER.readValue(jsonString, clazz);
		}
		catch (IOException e) {
			LOG.error("parse json string error:" + jsonString, e);
		}
		return null;
	}
    public static <T> T fromObject(Object o, Class<T> clazz) {
        if (o == null) {
            return null;
        }
        String jsonString = JsonUtil.toJson(o);
        try {
            return (T) OBJECT_MAPPER.readValue(jsonString, clazz);
        }
        catch (IOException e) {
            LOG.error("parse json string error:" + jsonString, e);
        }
        return null;
    }
	/**
	 * 读取集合如List/Map,且不是List<String>时.
	 * @param jsonString
	 * @param typeReference
	 * @return
	 */
	public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		try {
			return (T) OBJECT_MAPPER.readValue(jsonString, typeReference);
		}
		catch (IOException e) {
			LOG.error("parse json string error:" + jsonString, e);
		}
		return null;
	}
	/**
	 * 如果对象为Null,返回"null".
	 * 如果集合为空集合,返回"[]".
	 */
	public static String toJson(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		}
		catch (IOException e) {
			LOG.warn("write to json string error:" + object, e);
		}
		return null;
	}
	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public static ObjectMapper getMapper() {
		return OBJECT_MAPPER;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> fromJsonToMap(String json) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			map = objMapper.readValue(json, map.getClass());
		} catch (JsonParseException e) {
			LOG.error("Json串转换成对象出错：{}"+json);
			return null;
		} catch (JsonMappingException e) {
			LOG.error("Json串转换成对象出错：{}"+ json);
			return null;
		} catch (IOException e) {
			LOG.error("Json串转换成对象出错：{}"+ json);
			return null;
		}
		return map;
	}
	public static List<String> getShopName(String shopJson)
	{
		if (StringUtils.isNotBlank(shopJson)) {
			List<String> result = new ArrayList<String>();
			Map<String, String> shopMap = fromJson(shopJson,Map.class);
			Set<String> keys = shopMap.keySet();
			for(String s : keys)
			{
				result.add(shopMap.get(s));
			}
			return result;
			
		}else {
			return null;
		}
		
		
	}
	
	
}
