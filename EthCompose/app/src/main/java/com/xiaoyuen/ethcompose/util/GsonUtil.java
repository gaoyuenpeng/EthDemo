package com.xiaoyuen.ethcompose.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GsonUtil {

    public static String toString(Object object) {
        if (object == null) {
            return "";
        }
        return buildGson().toJson(object);
    }

    public static <T> T formJson(JSONObject json, Class<T> classOfT) {
        if (json == null) {
            return null;
        }
        return formJson(json.toString(), classOfT);
    }

    public static <T> T formJson(String json, Class<T> classOfT) {
        if (json.isEmpty()) {
            return null;
        }
        T model = buildGson().fromJson(json, (Type) classOfT);
        return model;
    }

    public static <T> ArrayList<T> formJsonToList(JSONArray json, Class<T> classOfT) {
        if (json == null) {
            return null;
        }
        return formJsonToList(json.toString(), classOfT);
    }

    public static <T> ArrayList<T> formJsonToList(String json, Class<T> classOfT) {
        try {
            JSONArray data = new JSONArray(json);
            if (data != null && data.length() > 0) {
                ArrayList<T> models = new ArrayList<T>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject obj = data.optJSONObject(i);
                    T model = formJson(obj, classOfT);
                    models.add(model);
                }
                return models;
            }
        } catch (Exception e) {

        }
        return null;
    }

    public static Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(long.class, LongTypeAdapter)
                .registerTypeAdapter(Long.class, LongTypeAdapter)
                .registerTypeAdapter(int.class, IntTypeAdapter)
                .registerTypeAdapter(Integer.class, IntTypeAdapter)
                .registerTypeAdapter(double.class, DoubleTypeAdapter)
                .registerTypeAdapter(Double.class, DoubleTypeAdapter)
                .create();
    }

    private static TypeAdapter<Number> LongTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return null;
                }
                return Long.parseLong(result);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    };

    private static TypeAdapter<Number> IntTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return null;
                }
                return Integer.parseInt(result);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    };

    private static TypeAdapter<Number> DoubleTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return null;
                }
                return Double.parseDouble(result);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    };
}
