package com.rarvinw.app.basic.androidboot.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;

/**
 * Created by newhope on 2016/4/5.
 */
public class
GsonHelper implements IJsonHelper {

    Gson gson;

    public GsonHelper() {

        TypeAdapter<String> STRING = new TypeAdapter<String>() {
            @Override
            public String read(JsonReader in) throws IOException {
                JsonToken peek = in.peek();
                if (peek == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
			      /* coerce booleans to strings for backwards compatibility */
                if (peek == JsonToken.BOOLEAN) {
                    return Boolean.toString(in.nextBoolean());
                }
                return in.nextString();
            }
            @Override
            public void write(JsonWriter out, String value) throws IOException {
                //out.value(value);
                if(value == null){
                    out.jsonValue(value);
                }else{
                    out.jsonValue("\""+value+"\"");
                }
            }
        };


        ExclusionStrategy myExclusionStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return fa.getName().startsWith("_");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
        gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
        gb.setExclusionStrategies(myExclusionStrategy);
       // gb.registerTypeAdapter(String.class, STRING);
        gb.disableHtmlEscaping();
        gson = gb.create();
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> String toJson(T t) {
        return gson.toJson(t);
    }

    @Override
    public <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
}
