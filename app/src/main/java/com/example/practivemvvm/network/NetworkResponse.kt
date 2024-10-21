package com.example.practivemvvm.network

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T, val responseCode: Int = 0) : NetworkResponse<T>()
    data class Failure(val message: String, val responseCode: Int = 0) : NetworkResponse<Nothing>()
}