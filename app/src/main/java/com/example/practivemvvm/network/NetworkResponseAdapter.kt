package com.example.practivemvvm.network

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import java.lang.reflect.Type

class NetworkResponseAdapter(
    private val type: Type
) : CallAdapter<Any?, Call<NetworkResponse<Any?>>> {
    override fun responseType(): Type = type

    private val voidMapper = { response: Response<Any?> ->
        NetworkResponse.Success(data = null, responseCode = response.code())
    }
    private val unitMapper = { response: Response<Any?> ->
        NetworkResponse.Success(data = Unit, responseCode = response.code())
    }

    private val typeMapper = { response: Response<Any?> ->
        val responseBody = response.body()
        if (responseBody == null) {
            NetworkResponse.Failure(message = response.message(), responseCode = response.code())
        } else {
            NetworkResponse.Success(
                data = responseBody,
                responseCode = response.code()
            )
        }
    }

    override fun adapt(call: Call<Any?>) = NetworkResponseCall(call, successMapper)

    private val successMapper: (Response<Any?>) -> NetworkResponse<Any?> = when (type) {
        Void::class.java -> voidMapper
        Unit::class.java -> unitMapper
        else -> typeMapper
    }

}