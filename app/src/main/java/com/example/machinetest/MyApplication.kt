package com.example.machinetest

import android.app.Application
import com.example.machinetest.data.network.MyApi
import com.example.machinetest.data.network.NetworkConnectionInterceptor
import com.example.machinetest.data.repositories.MainRepository
import com.example.machinetest.ui.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MyApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {

        import(androidXModule(this@MyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { MainRepository(instance()) }

        // MainActivity
        bind() from singleton { MainViewModelFactory(instance()) }
    }
}