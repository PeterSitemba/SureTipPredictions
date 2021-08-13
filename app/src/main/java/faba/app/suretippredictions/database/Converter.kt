package faba.app.suretippredictions.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import faba.app.suretippredictions.models.events.Events

class Converter {

    @TypeConverter
    fun toEvent(json: String): List<Events> {
        val type = object : TypeToken<List<Events>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(events: List<Events>): String {
        val type = object: TypeToken<List<Events>>() {}.type
        return Gson().toJson(events, type)
    }
}