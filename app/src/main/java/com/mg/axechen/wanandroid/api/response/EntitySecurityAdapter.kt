package com.mg.axechen.wanandroid.api.response

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.json.JSONObject
import java.lang.reflect.Type

class EntitySecurityAdapter : JsonDeserializer<Any> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any {
        return if (json.isJsonObject) {
            if (json.toString().contains("{}") && json.toString().length == 2) {
                JSONObject.NULL
            } else {
                Gson().fromJson(json, typeOfT)
            }
        } else {
            JSONObject.NULL
        }
    }
}