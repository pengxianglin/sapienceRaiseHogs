package com.rarvinw.app.basic.androidboot.utils;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Created by newhope on 2016/4/27.
 */
public class DateSerializer implements JsonSerializer<Date> {
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getTime());
    }
}
