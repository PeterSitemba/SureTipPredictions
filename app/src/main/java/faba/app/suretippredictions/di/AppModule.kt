package faba.app.suretippredictions.di


import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import faba.app.suretippredictions.database.PredictionsDao
import faba.app.suretippredictions.database.PredictionsRoomDatabase
import faba.app.suretippredictions.repository.PredictionsRepository
import faba.app.suretippredictions.service.Apollo
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi(@ApplicationContext appContext: Context) = Apollo(appContext)

    @Singleton
    @Provides
    fun provideRepository(api: Apollo, predictionsDao: PredictionsDao) =
        PredictionsRepository(api, predictionsDao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): PredictionsRoomDatabase =
        Room.databaseBuilder(
            appContext,
            PredictionsRoomDatabase::class.java,
            "surescore_predictions"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun providePredictionsDao(db: PredictionsRoomDatabase) = db.PredictionsDao()

}