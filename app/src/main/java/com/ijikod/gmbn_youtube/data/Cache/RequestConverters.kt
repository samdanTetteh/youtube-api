package com.ijikod.gmbn_youtube.data.Cache

import androidx.room.TypeConverter
import com.ijikod.gmbn_youtube.data.models.CommentItems
import com.ijikod.gmbn_youtube.data.models.Item
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


    private val commentItems : ParameterizedType = Types.newParameterizedType(List::class.java, CommentItems::class.java)
    private val listCommentsJsonAdapter: JsonAdapter<List<CommentItems>> = moshi.adapter(commentItems)


    @TypeConverter
    fun listCommentsToJsonStr(listMash: List<CommentItems>?): String? {
        return listCommentsJsonAdapter.toJson(listMash)
    }

    @TypeConverter
    fun jsonCommentsToListString(jsonStr: String?): List<CommentItems>? {
        return jsonStr?.let { listCommentsJsonAdapter.fromJson(jsonStr) }
    }


    @TypeConverter
    fun listMashToJsonStr(listMash: List<Item>?): String? {
        return listItemsJsonAdapter.toJson(listMash)
    }

    @TypeConverter
    fun jsonMashToListString(jsonStr: String?): List<Item>? {
        return jsonStr?.let { listItemsJsonAdapter.fromJson(jsonStr) }
    }

}