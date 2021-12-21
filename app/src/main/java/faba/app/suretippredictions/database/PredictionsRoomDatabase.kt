package faba.app.suretippredictions.database

import androidx.room.*

@Database(entities = [Prediction::class, Odds::class], version = 4, exportSchema = false)
@TypeConverters(Converter::class)
abstract class PredictionsRoomDatabase : RoomDatabase() {

    abstract fun PredictionsDao() : PredictionsDao

}
