package com.circle.util.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by keweiyang on 2017/3/16.
 */
public class JsonHelper {
    private JsonHelper() {

    }

    public static String object2Str(Object obj) {
        String retStr = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            retStr = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
        }


        return retStr;
    }

    public static Object str2Object(String str, Class<?> clazz)  {
        Object retObj = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            retObj = mapper.readValue(str, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retObj;
    }
}
