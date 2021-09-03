package faba.app.suretippredictions.service

import faba.app.suretippredictions.models.events.Events
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

const val BASE_URL = "https://apiv3.apifootball.com/"
const val matchEventUrl = "$BASE_URL?action=get_events&from=2021-08-29&to=2021-08-29&timezone=Africa/Nairobi&APIkey=9802a2b2af68749aab5cc42c5ca4881e8dd13ef91436dd1e4e6149246576e700"

interface RetrofitService {

    @GET(matchEventUrl)
    suspend fun getMatchEventsForDateRange(): Response<List<Events>>

}