package com.a1danz.feature_posts_feed_database.data.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.a1danz.feature_posts_feed_database.data.data.local.entites.PostPlaceEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.sql.Date
import java.util.Calendar
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(
    private val gson: Gson
) {

    // date converter
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let {
            Calendar.getInstance().apply { timeInMillis = it }
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Calendar?): Long? {
        return date?.timeInMillis
    }

    //  imgs list converter
    @TypeConverter
    fun listToString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToList(string: String): List<String> {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(string, listType)
    }

    // post_places list converter
    @TypeConverter
    fun convertToString(list: List<PostPlaceEntity>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(string: String): List<PostPlaceEntity> {
        val type: Type = object : TypeToken<List<PostPlaceEntity?>?>() {}.type
        return gson.fromJson(string, type)
    }
}