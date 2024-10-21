package com.example.practivemvvm.network


import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor():
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        // If token has been saved, add it to the request

        runBlocking {
            if (request.header("Authorization") == null) {
                requestBuilder.addHeader("Authorization", "")
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}
