package faba.app.suretippredictions.di


import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideRepository(api: Apollo) = PredictionsRepository(api)

}