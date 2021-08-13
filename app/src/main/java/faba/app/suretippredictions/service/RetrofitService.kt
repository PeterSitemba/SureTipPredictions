package faba.app.suretippredictions.service

import faba.app.suretippredictions.models.events.Events
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

const val BASE_URL = "https://apiv3.apifootball.com/"
const val matchEventUrl = "$BASE_URL?action=get_events&from=2021-08-11&to=2021-08-11&timezone=Africa/Nairobi&APIkey=1d6b403d5466ef49debd6ff0c505420880d36d828c2e587c49f7f46fd48eb30c"

interface RetrofitService {

    @GET(matchEventUrl)
    suspend fun getMatchEventsForDateRange(): Response<List<Events>>

/*
    companion object{
        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://apiv3.apifootball.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
*/

}