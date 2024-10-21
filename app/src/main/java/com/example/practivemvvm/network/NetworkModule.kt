package com.example.practivemvvm.network

import com.example.practivemvvm.network.api.MiddlewareRestService
import com.example.practivemvvm.repository.UserRepository
import com.example.practivemvvm.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    var BASE_URL = "https://api.github.com"

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addCallAdapterFactory(NetworkResponseAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideNetworkClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor).also {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                it.addInterceptor(logging)
            }.build()
    }

    @Provides
    fun provideMiddlewareRestService(retrofit: Retrofit) =
        retrofit.create(MiddlewareRestService::class.java)

    @Provides
    fun provideUserRepository(apiService: MiddlewareRestService): UserRepository =
        UserRepositoryImpl(apiService)

}