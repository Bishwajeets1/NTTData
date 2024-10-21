package com.example.practivemvvm.network
sealed class Response<out T> {
    data class Loading<T>(val loading: String) : Response<T>()
    data class Success<T>(val data:T) : Response<T>()
    data class Failure(val error:String, val responseCode: Int = 0) : Response<Nothing>()
}