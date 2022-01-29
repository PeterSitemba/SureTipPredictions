package faba.app.suretippredictions.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    @SuppressLint("SimpleDateFormat")
    fun DateFormater(milliseconds: Long?): String? {
        milliseconds?.let {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val calendar: Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(it)
            return formatter.format(calendar.getTime())
        }
        return null
    }

    fun formatGameTime(gameTime: String): String {

        val df = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(gameTime)
        df.timeZone = TimeZone.getDefault()
        return df.format(date)

    }

    @SuppressLint("SimpleDateFormat")
    fun DateFormaterDayOnly(milliseconds: Long?): String? {
        milliseconds?.let {
            val formatter = SimpleDateFormat("dd")
            val calendar: Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(it)
            return formatter.format(calendar.getTime())
        }
        return null
    }
}