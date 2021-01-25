package com.example.machinetest.data.repositories

import SafeApiRequest
import androidx.lifecycle.MutableLiveData
import com.example.machinetest.data.network.MyApi
import com.example.machinetest.data.network.responses.LocationResponse
import com.example.machinetest.util.Constants
import com.google.gson.JsonObject

class MainRepository(val api: MyApi) : SafeApiRequest() {
    // Get location details
    suspend fun getLocation(appType: String, channelType: String, country: String): MutableLiveData<LocationResponse> {
        val otpResponse = MutableLiveData<LocationResponse>()
        val requestPayLoad = JsonObject()
        requestPayLoad.addProperty("app_type", appType)
        requestPayLoad.addProperty("channel_type", channelType)
        requestPayLoad.addProperty("country", country)

        try {
            otpResponse.value = apiRequest { api.getLocation(requestPayLoad) }
        } catch (e: Exception) {
            otpResponse.value = null
            Constants.NETWORK_ERROR_MSG = e.message!!
        }
        return otpResponse
    }
}