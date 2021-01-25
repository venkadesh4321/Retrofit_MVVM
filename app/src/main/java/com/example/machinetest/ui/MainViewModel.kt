package com.example.machinetest.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.machinetest.data.network.ApiCallBack
import com.example.machinetest.data.repositories.MainRepository
import com.example.machinetest.util.Constants
import com.example.machinetest.util.Coroutines
import com.example.machinetest.util.NoInternetException

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {
    var apiCallBack: ApiCallBack? = null

    // getting location details
    fun getLocationDetails(appType: String, channelType: String, country: String) {
        apiCallBack?.onStarted()
        try {
            Coroutines.main {
                val response = repository.getLocation(appType, channelType, country)

                if (response.value != null) {
                    // Log.e("response====>>", "getLocationDetails: " + response.value)
                    apiCallBack?.onSuccess(response.value!!)
                } else {
                    apiCallBack?.onError(Constants.NETWORK_ERROR_MSG)
                }
            }
        } catch (e: NoInternetException) {
            apiCallBack?.onError(e.message!!)
        }
    }
}