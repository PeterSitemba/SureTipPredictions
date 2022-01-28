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
}