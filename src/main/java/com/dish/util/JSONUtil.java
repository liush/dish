package com.dish.util;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-8
 * Time: 上午11:51
 * json工具类
 */
public class JSONUtil {

    public static <T> T parseJSONObject(String json, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(tClass);
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> List<T> parseJSONArray(String json, Class<T> aClass) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerSubtypes(aClass);
        try {
            JsonNode jsonNodes = mapper.readTree(json);
            if (jsonNodes.isArray()) {
                ArrayList<T> list = new ArrayList<T>();
                for (JsonNode jsonNode : jsonNodes) {
                    T t = mapper.readValue(jsonNode, aClass);
                    list.add(t);
                }
                return list;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> String parseToString(T t) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(t);
        } catch (IOException e) {
            return null;
        }
    }
}
