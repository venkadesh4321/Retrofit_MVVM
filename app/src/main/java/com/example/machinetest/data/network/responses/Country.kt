package com.example.machinetest.data.network.responses

import android.provider.ContactsContract

data class Country(
        val country_name : String?,
        val name : String?,
        val level : String?,
        var state: ArrayList<State>? = null
)