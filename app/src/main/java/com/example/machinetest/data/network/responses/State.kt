package com.example.machinetest.data.network.responses

import android.provider.ContactsContract

data class State(
        val state_name : String?,
        val name : String?,
        val level : String?,
        var city: ArrayList<City>? = null
)