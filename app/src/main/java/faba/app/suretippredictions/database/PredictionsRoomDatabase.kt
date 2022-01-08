package faba.app.suretippredictions.database

import androidx.room.*

@Database(entities = [Prediction::class], version = 14, exportSchema = false)
@TypeConverters(Converter::class)
abstract class PredictionsRoomDatabase : RoomDatabase() {

    abstract fun PredictionsDao() : PredictionsDao

}
