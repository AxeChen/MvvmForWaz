package com.mg.axechen.libdata

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.net.Proxy
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    private const val CONNECTION_TIME_OUT = 10L
    private const val READ_TIME_OUT = 10L


    fun buildOkHttpClient(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .proxy(Proxy.NO_PROXY)
    }

    fun buildRetrofit(baseUrl: String, builder: OkHttpClient.Builder): Retrofit {
        val client = builder.build()

        val exclusionStrategy: ExclusionStrategy = object : ExclusionStrategy {
            override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
                return false
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return clazz == Field::class.java || clazz == Method::class.java
            }
        }

        val gson = GsonBuilder().disableHtmlEscaping()
            .addSerializationExclusionStrategy(exclusionStrategy)
            .addDeserializationExclusionStrategy(exclusionStrategy)
            .create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
    }

    fun buildRetrofit(baseUrl: String): Retrofit {
        val client = buildOkHttpClient()
            .build()
        val exclusionStrategy: ExclusionStrategy = object : ExclusionStrategy {
            override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
                return false
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return clazz == Field::class.java || clazz == Method::class.java
            }
        }

        val gson = GsonBuilder().disableHtmlEscaping()
            .addSerializationExclusionStrategy(exclusionStrategy)
            .addDeserializationExclusionStrategy(exclusionStrategy)
            .create()

        return Retrofit.Builder().baseUrl(baseUrl).client(client).build()
    }

}