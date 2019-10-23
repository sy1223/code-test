package com.example.codetest.service

import com.example.codetest.model.Transaction
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

    companion object {
        val baseUrl: String = "http://165.22.246.120/"

        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(APIService::class.java)
        }
    }

    @FormUrlEncoded
    @POST("api/v2/transfer")
    fun transfer(@Field("from") from: String,
                 @Field("to") to: String,
                 @Field("amount") amount: Double): Call<Transaction>
}