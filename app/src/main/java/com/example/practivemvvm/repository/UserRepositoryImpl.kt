package com.example.practivemvvm.repository

import com.example.practivemvvm.model.DataModelClass
import com.example.practivemvvm.network.NetworkResponse
import com.example.practivemvvm.network.Response
import com.example.practivemvvm.network.api.MiddlewareRestService
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val middlewareRestService: MiddlewareRestService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {
    override suspend fun getUserProfileData(): Response<ArrayList<DataModelClass>> {
        middlewareRestService.getUserProfileData().let {
            return withContext(ioDispatcher) {
                when (it) {
                    is NetworkResponse.Failure -> Response.Failure(it.message)
                    is NetworkResponse.Success -> Response.Success(it.data)
                }
            }

        }
    }
}