package com.example.ecommerceuserpanel.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.ecommerceuserpanel.R
import com.google.firebase.auth.FirebaseAuth

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({

            if (user == null){
                val mainIntent = Intent(this, LogInScreen::class.java)
                startActivity(mainIntent)
            }else{
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }

            finish()
        }, 3400)
    }
}