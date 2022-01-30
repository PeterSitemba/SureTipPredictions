package faba.app.suretippredictions.service

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import faba.app.suretippredictions.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class Apollo(private val context: Context) {
    private var apClient: ApolloClient? = null
    private var httpClient: OkHttpClient? = null
    private val TIMEOUT_IN_SECONDS: Long = 60

    fun getApolloClient(): ApolloClient {
        apClient?.let {
            return it
        } ?: kotlin.run {
            apClient = ApolloClient.Builder()
                .okHttpClient(getOkhttpClient(context)!!)
                .serverUrl(Constants.URL)
                .build()
        }
        return apClient!!
    }

    private fun getOkhttpClient(context: Context): OkHttpClient? {
        httpClient?.let {
            return it
        } ?: kotlin.run {
            httpClient = OkHttpClient.Builder()
                //Adding HttpLoggingInterceptor() to see the response body and the results.
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                //.addInterceptor(getLoggingInterceptor())
                .addInterceptor(AuthorizationInterceptor())
                .addInterceptor(NetworkConnectionInterceptor(context))
                .build()

        }
        return httpClient
    }

    private fun getLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            level = HttpLoggingInterceptor.Level.BODY
            redactHeader("Response:")
        }
}
