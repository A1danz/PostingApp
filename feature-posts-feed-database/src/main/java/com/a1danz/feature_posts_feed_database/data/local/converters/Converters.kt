package com.a1danz.feature_posts_feed_database.data.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.a1danz.feature_places_info.domain.model.PostPlaceType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
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
    fun convertToString(list: List<PostPlaceType>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(string: String): List<PostPlaceType> {
        val type: Type = object : TypeToken<List<PostPlaceType?>?>() {}.type
        return gson.fromJson(string, type)
    }
}