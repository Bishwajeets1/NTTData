package com.example.practivemvvm.repository

import com.example.practivemvvm.model.DataModelClass
import com.example.practivemvvm.network.Response
import com.google.gson.JsonObject


interface UserRepository {
    suspend fun getUserProfileData(): Response<ArrayList<DataModelClass>>

}