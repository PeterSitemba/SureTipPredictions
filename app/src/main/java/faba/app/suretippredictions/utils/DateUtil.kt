package faba.app.suretippredictions.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    @SuppressLint("SimpleDateFormat")
    fun getDay(theDate: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.parse(theDate)
        return SimpleDateFormat("EEEE").format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatToDateAndMonth(theDate: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = formatter.parse(theDate)
        return SimpleDateFormat("dd-MM").format(date)
    }



    @SuppressLint("SimpleDateFormat")
    fun dateFormatter(milliseconds: Long?): String? {
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
    fun dateFormatterDayOnly(milliseconds: Long?): String? {
        milliseconds?.let {
            val formatter = SimpleDateFormat("dd")
            val calendar: Calendar = Calendar.getInstance()
            calendar.setTimeInMillis(it)
            return formatter.format(calendar.getTime())
        }
        return null
    }

    @SuppressLint("SimpleDateFormat")
    fun currentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }
}