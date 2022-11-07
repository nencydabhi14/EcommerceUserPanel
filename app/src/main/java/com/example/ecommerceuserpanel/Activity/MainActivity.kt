package com.example.ecommerceuserpanel.Activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.ecommerceuserpanel.BordCast.Brodcast
import com.example.ecommerceuserpanel.BordCast.Brodcast.Companion.status
import com.example.ecommerceuserpanel.Fragments.CartFragment
import com.example.ecommerceuserpanel.Fragments.HomeFragment
import com.example.ecommerceuserpanel.Fragments.ProfileFragment
import com.example.ecommerceuserpanel.Fragments.SearchFragment
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var mainbinding: ActivityMainBinding
    lateinit var receiver: Brodcast

    override fun onCreate(savedInstanceState: Bundle?) {
        mainbinding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(mainbinding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.rama)

        receiver = Brodcast()
        InterNetAccessDialog()
        Bottom_Navigation()


        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also {
            registerReceiver(receiver,it)
        }
    }

    fun Bottom_Navigation() {
        val fragmentManager: FragmentManager = supportFragmentManager

        val fragment_home: Fragment = HomeFragment(this)
        val fragment2: Fragment = CartFragment(this)
        val fragment3: Fragment = SearchFragment(this)
        val fragment4: Fragment = ProfileFragment(this)


        mainbinding.bottomNavigation.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.home -> fragment = fragment_home
                R.id.cart -> fragment = fragment2
                R.id.search -> fragment = fragment3
                R.id.profile -> fragment = fragment4
            }
            fragmentManager.beginTransaction().replace(R.id.wrapper, fragment).commit()
            true
        }
        mainbinding.bottomNavigation.selectedItemId = R.id.home

    }

    fun InterNetAccessDialog(){
        var dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.dialog_internet)

        if (status == true){
            Log.e("TAG", "onReceive: net is On")
        }
        else{
//            dialog.show()
        }
    }
}