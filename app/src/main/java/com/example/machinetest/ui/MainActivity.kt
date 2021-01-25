package com.example.machinetest.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.machinetest.R
import com.example.machinetest.data.network.ApiCallBack
import com.example.machinetest.data.network.responses.LocationResponse
import com.example.machinetest.databinding.ActivityMainBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware, ApiCallBack {
    // injection
    override val kodein by kodein()
    private val mainViewModelFactory: MainViewModelFactory by instance()

    private val TAG = "MainActivity"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private var locationResponseDetails: LocationResponse? = null
    private var selectedCountryPostion: Int? = null
    private var selectedStatePostion: Int? = null
    private var countryNameAdapter: ArrayAdapter<String>? = null
    val countryNameList = ArrayList<String>()
    val stateNameList = ArrayList<String>()
    val cityNameList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViews()

        mainViewModel.getLocationDetails("app1", "radio", "")

        activityMainBinding.countrySpinner.setTitle("Select country")
        activityMainBinding.stateSpinner.setTitle("Select state")
        activityMainBinding.citySpinner.setTitle("Select city")

        // country selection
        activityMainBinding.countrySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                val stateNameList = ArrayList<String>()

                for (response in locationResponseDetails!!.data!!.country!![position].state!!) {
                    stateNameList.add(response.state_name!!)
                }
                selectedCountryPostion = position
                Log.e(TAG, "states: " + stateNameList)
                loadStates(stateNameList)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }


        // state selection
        activityMainBinding.stateSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                Log.e(TAG, "onItemSelected: " + position)
                val cityNameList = ArrayList<String>()

                for (response in locationResponseDetails!!.data!!.country!![selectedCountryPostion!!].state!![position].city!!) {
                    cityNameList.add(response.city_name!!)
                }
                selectedStatePostion = position
                Log.e(TAG, "cities: " + cityNameList)
                loadCities(cityNameList)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        // city selection
        activityMainBinding.citySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {

                activityMainBinding.cityNameTextView.text =
                    locationResponseDetails!!.data!!.country!![selectedCountryPostion!!].state!![selectedStatePostion!!].city!![position].city_name
                activityMainBinding.latNameTextView.text =
                    locationResponseDetails!!.data!!.country!![selectedCountryPostion!!].state!![selectedStatePostion!!].city!![position].latitude
                activityMainBinding.langNameTextView.text =
                    locationResponseDetails!!.data!!.country!![selectedCountryPostion!!].state!![selectedStatePostion!!].city!![position].longitude
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun bindViews() {
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.apiCallBack = this
    }

    private fun loadStates(stateNameList: ArrayList<String>) {
        val stateNameAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, stateNameList)
        stateNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityMainBinding.stateSpinner.adapter = stateNameAdapter
    }

    private fun loadCities(cityNameList: ArrayList<String>) {
        val cityNameAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, cityNameList)
        cityNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityMainBinding.citySpinner.adapter = cityNameAdapter
    }

    override fun onStarted() {
        activityMainBinding.progressBar.visibility = View.VISIBLE
    }

    override fun onError(message: String) {
        activityMainBinding.progressBar.visibility = View.GONE
    }

    override fun onSuccess(locationResponse: LocationResponse) {
        activityMainBinding.progressBar.visibility = View.GONE
        // Log.e(TAG, "result==> "+ locationResponse)
        locationResponseDetails = locationResponse
        val countryNameList = ArrayList<String>()

        for (response in locationResponse.data!!.country!!) {
            countryNameList.add(response.country_name!!)
        }

        val countryNameAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, countryNameList)
        countryNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityMainBinding.countrySpinner.adapter = countryNameAdapter


    }
}