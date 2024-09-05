package com.gobinda.assignment.keeper.data.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {
    @get:Synchronized
    var adapter: Retrofit? = null
        private set

    val api: RestApi
        @Synchronized get() {
            if(adapter == null) {
                initRestAdapter()
            }
            return adapter!!.create(RestApi::class.java)
        }


    @Synchronized
    private fun initRestAdapter() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            /// need to make change on build confit
            if (true) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        // use OkHttp client
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                requestBuilder.method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        adapter = Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://www.jsonkeeper.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
          /*  .addCallAdapterFactory(
                RxJava2CallAdapterFactory
                    .createWithScheduler(Schedulers.io())
            )*/
            .build()
    }



}