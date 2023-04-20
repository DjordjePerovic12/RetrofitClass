package com.djordje.retrofitclass

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {

    val api: TodoApi by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
            .build().create(TodoApi::class.java)
    }

    //addConverterFactory - Retrofit mora da zna na koji nacin da preuzme JSON sa APIJA i poslije parsira isti taj JSON u data class
//GSON alat za parsiranje JSON-a
//callAdapterFactory - koristimo ako kasnije zelimo Response objekat da parsiramo na specifican nacin, npr ako zelimo response da dobijemo kao RX Javastream
//pravimo ovu instancu TodoApi klase kako bi smo preko nje mogli da izvrsavamo nase api pozive


}