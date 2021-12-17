package faba.app.suretippredictions.service

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content-type", "application/json")
            .addHeader("x-api-key", "da2-omqrsadcubglllpaxjb4pu6yx4")
            .build()

        return chain.proceed(request)
    }
}