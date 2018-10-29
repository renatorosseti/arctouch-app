package com.arctouch.codechallenge.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

class InternetBuilder {
    companion object {
        var isInternetAvailable = fun(context: Context): Boolean {
            try {
                val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val i = conMgr?.activeNetworkInfo
                return (i!= null && i.isConnected && i.isAvailable)
            } catch (e: Exception) {
                Log.e("InternetBuilder", "Internet failed due to $e")
            }
            return false
        }
    }

}