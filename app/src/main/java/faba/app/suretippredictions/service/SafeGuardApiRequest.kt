package faba.app.suretippredictions.service

import com.apollographql.apollo.api.Response
import faba.app.core.ApiException
import faba.app.core.NoInternetException
import org.json.JSONException

abstract class SafeGuardApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        try {
            if (!response.hasErrors()) {
                if (response.data != null) {
                    return response.data!!
                } else {
                    throw ApiException(response.errors?.get(0)!!.message)
                }
            } else {
                val error = response.errors?.get(0)?.message
                val message = StringBuilder()

                error?.let {
                    try {
                        message.append(error)
                    } catch (e: JSONException) {
                        throw ApiException(message.toString())
                    }
                }

                throw ApiException(message.toString())
            }
        }catch (e: NoInternetException){
            throw e
        }
    }
}