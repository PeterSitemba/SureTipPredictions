package faba.app.suretippredictions.database

import androidx.room.*

@Database(entities = arrayOf(MatchEvents::class), version = 4, exportSchema = false)
@TypeConverters(Converter::class)
abstract class PredictionsRoomDatabase : RoomDatabase() {

    abstract fun PredictionsDao() : PredictionsDao


/*
    companion object{
        @Volatile
        private var INSTANCE: PredictionsRoomDatabase? = null

        fun getDatabase(context : Context) : PredictionsRoomDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PredictionsRoomDatabase::class.java,
                    "suretips_predictions"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
*/

}