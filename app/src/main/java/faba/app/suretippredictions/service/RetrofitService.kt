package faba.app.suretippredictions.service

import faba.app.suretippredictions.models.events.Events
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

const val BASE_URL = "https://apiv3.apifootball.com/"
const val matchEventUrl = "$BASE_URL?action=get_events&from=2021-08-20&to=2021-08-20&timezone=Africa/Nairobi&APIkey=8d384aa5f27bf83b7d9835be1d2ff01e2909c92a1f50af5c4466b6d0ead7d6a8"

interface RetrofitService {

    @GET(matchEventUrl)
    suspend fun getMatchEventsForDateRange(): Response<List<Events>>

}