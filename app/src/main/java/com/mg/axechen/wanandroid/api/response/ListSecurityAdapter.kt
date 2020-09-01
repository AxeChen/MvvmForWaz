package com.mg.axechen.wanandroid.api.response

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

class ListSecurityAdapter : JsonDeserializer<MutableList<*>> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): MutableList<*> {
        return if (json.isJsonArray) {
            Gson().fromJson(json, typeOfT)
        } else {
            Collections.EMPTY_LIST
        }
    }
}