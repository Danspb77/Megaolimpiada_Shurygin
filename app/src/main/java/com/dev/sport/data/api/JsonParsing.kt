package com.dev.sport.data.api

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

object JsonParsing {
    private val gson = Gson()

    fun <T> parseList(
        element: JsonElement,
        typeToken: TypeToken<List<T>>,
        arrayKeys: List<String>
    ): List<T> {
        return when {
            element.isJsonArray -> gson.fromJson(element, typeToken.type)
            element.isJsonObject -> {
                val obj = element.asJsonObject
                val array = findFirstArray(obj, arrayKeys)
                if (array != null) {
                    gson.fromJson(array, typeToken.type)
                } else {
                    emptyList()
                }
            }
            else -> emptyList()
        }
    }

    private fun findFirstArray(obj: JsonObject, keys: List<String>): JsonArray? {
        for (key in keys) {
            val node = obj.get(key)
            if (node != null && node.isJsonArray) {
                return node.asJsonArray
            }
        }
        return null
    }
}
