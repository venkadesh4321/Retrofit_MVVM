package com.example.machinetest.data.network

import com.example.machinetest.data.network.responses.LocationResponse

interface ApiCallBack {
    fun onStarted()
    fun onError(message: String)
    fun onSuccess(locationResponse: LocationResponse)
}