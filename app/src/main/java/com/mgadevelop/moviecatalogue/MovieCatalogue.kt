package com.mgadevelop.moviecatalogue

import android.app.Application
import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import java.util.*

class MovieCatalogue : Application() {

    lateinit var context: Context
    override fun onCreate() {
        super.onCreate()
        val okHttpClient = OkHttpClient.Builder().addNetworkInterceptor(
            HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message -> Log.d("API", message) }).setLevel(
                HttpLoggingInterceptor.Level.BODY)).build()
        AndroidNetworking.initialize(applicationContext, okHttpClient)

        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        context = applicationContext
    }

    companion object {
        const val MOVIE_DB_API_KEY = "943a99dcb774d42a1e274fd8becb5857"
    }

    fun getLanguage():String{
        //language en=english in=indonesia
        if(Locale.getDefault().language.toLowerCase().equals("in")){
            return "id"
        }else{
            return "en-US"
        }
    }
}