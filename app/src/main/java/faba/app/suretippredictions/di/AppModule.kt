package faba.app.suretippredictions.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import faba.app.suretippredictions.database.PredictionsDao
import faba.app.suretippredictions.database.PredictionsRoomDatabase
import faba.app.suretippredictions.repository.PredictionsRepository
import faba.app.suretippredictions.service.RetrofitService
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://apiv3.apifootball.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMatcheEventsService(retrofit: Retrofit): RetrofitService =
        retrofit.create(RetrofitService::class.java)

    @Singleton
    @Provides
    fun provideRepository(retrofitService: RetrofitService, predictionsDao: PredictionsDao) =
        PredictionsRepository(retrofitService, predictionsDao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) : PredictionsRoomDatabase =
        Room.databaseBuilder(
            appContext,
            PredictionsRoomDatabase::class.java,
            "suretips_predictions"
        ).build()

    @Singleton
    @Provides
    fun providePredictionsDao(db: PredictionsRoomDatabase) = db.PredictionsDao()

}