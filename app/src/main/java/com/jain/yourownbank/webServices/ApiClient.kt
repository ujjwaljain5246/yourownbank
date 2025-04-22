package com.jain.yourownbank.webServices

import android.util.Log
import com.jain.yourownbank.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val retrofitWithOutToken by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_PATH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClientWithOutToken())
            .build()
    }

    private val retrofitWithToken by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_PATH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClientWithToken())
            .build()
    }

    // Get the retrofit object without token
    fun createServiceWithOutToken() : ApiServices {
        return retrofitWithOutToken.create(ApiServices::class.java)
    }

    // Get the retrofit object without token
    fun createServiceWithToken() : ApiServices {
        return retrofitWithToken.create(ApiServices::class.java)
    }

    // Intercept header interceptor and okhttp logging interceptor
    private fun provideOkHttpClientWithToken(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideHeaderInterceptorWithToken()) // Add headers
            .addInterceptor(provideLoggingInterceptor()) // Add logging
            .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS) // Read timeout
            .build()
    }

    // Intercept header interceptor and okhttp logging interceptor
    private fun provideOkHttpClientWithOutToken(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideHeaderInterceptorWithOutToken()) // Add headers
            .addInterceptor(provideLoggingInterceptor()) // Add logging
            .connectTimeout(30, TimeUnit.SECONDS) // Connection timeout
            .readTimeout(30, TimeUnit.SECONDS) // Read timeout
            .build()
    }

    // Header Interceptor for sign in and sign up end points for adding default headers
    private fun provideHeaderInterceptorWithOutToken(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type",Constants.CONTENT_TYPE)
                .build()
            chain.proceed(request)
        }
    }

    // Header Interceptor for other than sign in and sign up end points for adding default headers
    private fun provideHeaderInterceptorWithToken(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("token",Constants.TOKEN)
                .build()
            chain.proceed(request)
        }
    }

    // Logging Interceptor for debugging API calls
    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }





}