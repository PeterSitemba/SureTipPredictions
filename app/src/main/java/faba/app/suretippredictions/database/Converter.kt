package faba.app.suretippredictions.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {

    /*@TypeConverter
    fun toEvent(json: String): Events {
        val type = object : TypeToken<Events>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(events: Events): String {
        val type = object: TypeToken<Events>() {}.type
        return Gson().toJson(events, type)
    }*/
}