package com.example.practivemvvm.network.api


import com.example.practivemvvm.model.DataModelClass
import com.example.practivemvvm.network.NetworkResponse
import com.google.gson.JsonObject
import retrofit2.http.GET

interface MiddlewareRestService {
    @GET("/users/google/repos")
    suspend fun getUserProfileData(): NetworkResponse<ArrayList<DataModelClass>>

}