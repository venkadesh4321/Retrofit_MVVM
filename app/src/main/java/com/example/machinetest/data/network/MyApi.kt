package com.example.machinetest.data.network

import com.example.machinetest.data.network.responses.LocationResponse
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface MyApi {
    // get location
    @POST("api/getLocations")
    suspend fun getLocation(
        @Body jsonObject: JsonObject
    ): Response<LocationResponse>

    /*  @GET("getUserCountByCommId/{comm_id}")
      suspend fun getUserCount(@Path("comm_id") commId: String
      ): Response<ArrayList<UserCount>>

      @GET("getUserByID/{user_id}")
      suspend fun getUserById(@Path("user_id") userId: String
      ): Response<ArrayList<User>>

      @GET("getUserHistoryByPhone/")
      suspend fun getUserHistory(@Query("contact_phone") contactPhone: String
      ): Response<ArrayList<UserHistory>>*/

    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi {
            val okkHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okkHttpClient)
                .baseUrl(networkConnectionInterceptor.STAGING)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}