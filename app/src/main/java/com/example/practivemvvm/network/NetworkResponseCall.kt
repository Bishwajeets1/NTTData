package com.example.practivemvvm.network

import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResponseCall(
    private val delegate: Call<Any?>,
    private val successMapper: (Response<Any?>) -> NetworkResponse<Any?>
) : Call<NetworkResponse<Any?>> {
    override fun clone() = NetworkResponseCall(delegate.clone(), successMapper)
    override fun execute(): Response<NetworkResponse<Any?>> {
        var result = try {
            val response = delegate.execute()
            successMapper(response)
        } catch (e: IOException) {
            NetworkResponse.Failure(message = e.message.toString())
        }
        return Response.success(result)
    }

    override fun enqueue(callback: Callback<NetworkResponse<Any?>>) =
        delegate.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                val result = successMapper(response)
                callback.onResponse(this@NetworkResponseCall, Response.success(result))
            }

            override fun onFailure(call: Call<Any?>, throwable: Throwable) {
                NetworkResponse.Failure(message = throwable.message.toString())
            }
        })

    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}