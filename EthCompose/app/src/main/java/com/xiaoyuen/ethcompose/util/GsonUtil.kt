package com.xiaoyuen.ethcompose.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

object GsonUtil {

    fun toString(obj: Any?): String? {
        return if (obj == null) {
            ""
        } else buildGson().toJson(obj)
    }

    fun <T> formJson(json: JSONObject?, classOfT: Class<T>?): T? {
        return if (json == null) {
            null
        } else formJson(json.toString(), classOfT)
    }

    fun <T> formJson(json: String, classOfT: Class<T>?): T? {
        return if (json.isEmpty()) {
            null
        } else buildGson().fromJson(json, classOfT as Type?)
    }

    fun <T> formJsonToList(json: JSONArray?, classOfT: Class<T>?): ArrayList<T>? {
        return if (json == null) {
            null
        } else formJsonToList(json.toString(), classOfT)
    }

    fun <T> formJsonToList(json: String?, classOfT: Class<T>?): ArrayList<T>? {
        try {
            val data = JSONArray(json)
            if (data.length() > 0) {
                val models = ArrayList<T>()
                for (i in 0 until data.length()) {
                    val obj = data.optJSONObject(i)
                    val model: T? = formJson(obj, classOfT)
                    model?.let {
                        models.add(model)
                    }
                }
                return models
            }
        } catch (e: Exception) {
        }
        return null
    }

    fun buildGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                Long::class.javaPrimitiveType,
                LongTypeAdapter
            )
            .registerTypeAdapter(
                Long::class.java,
                LongTypeAdapter
            )
            .registerTypeAdapter(
                Int::class.javaPrimitiveType,
                IntTypeAdapter
            )
            .registerTypeAdapter(
                Int::class.java,
                IntTypeAdapter
            )
            .registerTypeAdapter(
                Double::class.javaPrimitiveType,
                DoubleTypeAdapter
            )
            .registerTypeAdapter(
                Double::class.java,
                DoubleTypeAdapter
            )
            .create()
    }

    private val LongTypeAdapter: TypeAdapter<Number?> =
        object : TypeAdapter<Number?>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Number?) {
                out.value(value)
            }

            @Throws(IOException::class)
            override fun read(input: JsonReader): Number? {
                if (input.peek() == JsonToken.NULL) {
                    input.nextNull()
                    return null
                }
                return try {
                    val result = input.nextString()
                    if ("" == result) {
                        null
                    } else result.toLong()
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }

    private val IntTypeAdapter: TypeAdapter<Number?> =
        object : TypeAdapter<Number?>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Number?) {
                out.value(value)
            }

            @Throws(IOException::class)
            override fun read(input: JsonReader): Number? {
                if (input.peek() == JsonToken.NULL) {
                    input.nextNull()
                    return null
                }
                return try {
                    val result = input.nextString()
                    if ("" == result) {
                        null
                    } else result.toInt()
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }

    private val DoubleTypeAdapter: TypeAdapter<Number?> =
        object : TypeAdapter<Number?>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter, value: Number?) {
                out.value(value)
            }

            @Throws(IOException::class)
            override fun read(`in`: JsonReader): Number? {
                if (`in`.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return null
                }
                return try {
                    val result = `in`.nextString()
                    if ("" == result) {
                        null
                    } else result.toDouble()
                } catch (e: NumberFormatException) {
                    throw JsonSyntaxException(e)
                }
            }
        }
}