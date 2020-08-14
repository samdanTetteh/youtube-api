package com.ijikod.gmbn_youtube.data.Cache

import androidx.room.TypeConverter
import com.ijikod.gmbn_youtube.data.modules.Item
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType

class RequestConverters {

    private val moshi = Moshi.Builder().build()
    private val listString : ParameterizedType = Types.newParameterizedType(List::class.java, String::class.java)
    private val listStringJsonAdapter: JsonAdapter<List<String>> = moshi.adapter(listString)


    private val listItems : ParameterizedType = Types.newParameterizedType(List::class.java, Item::class.java)
    private val listItemsJsonAdapter: JsonAdapter<List<Item>> = moshi.adapter(listItems)


    @TypeConverter
    fun listMashToJsonStr(listMash: List<Item>?): String? {
        return listItemsJsonAdapter.toJson(listMash)
    }

    @TypeConverter
    fun jsonMashToListString(jsonStr: String?): List<Item>? {
        return jsonStr?.let { listItemsJsonAdapter.fromJson(jsonStr) }
    }

}