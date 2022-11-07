package com.example.ecommerceuserpanel.BordCast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast

class Brodcast : BroadcastReceiver() {

    companion object{
        var status: Boolean? = null
    }

    override fun onReceive(context: Context?, p1: Intent?) {
        status = isNetworkAvailable(context!!)

        if (status==true){
            Log.e("TAG", "onReceive: net is On")
        }
        else{
            Toast.makeText(context, "Please On You Mobile Data ", Toast.LENGTH_SHORT).show()
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}