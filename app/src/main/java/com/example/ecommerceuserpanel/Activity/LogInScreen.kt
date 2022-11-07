package com.example.ecommerceuserpanel.Activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.ActivityLogInScreenBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlin.math.log

class LogInScreen : AppCompatActivity() {

    lateinit var logInScreenBinding: ActivityLogInScreenBinding

    private val RC_SIGN_IN = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        logInScreenBinding = ActivityLogInScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(logInScreenBinding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        logInScreenBinding.signUp.setOnClickListener {
            var intent = Intent(this, Register_Page::class.java)
            startActivity(intent)
        }

        logInScreenBinding.logIn.setOnClickListener {
            signingAccount(
                logInScreenBinding.emailEdt.text.toString(),
                logInScreenBinding.passwordEdt.text.toString()
            )
//            logInScreenBinding.Progressbar.isVisible = true
//            logInScreenBinding.signingTxt.isVisible = false
        }

        logInScreenBinding.logInWithGoogle.setOnClickListener {
            SignIngWithGoogle()
        }

    }

    fun SignIngWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        var googleApiClient =
            GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()

        val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
                var account = result?.signInAccount
                fireToken(account?.idToken.toString())
            }
        }
    }

    fun fireToken(idToken: String) {

        var crd = GoogleAuthProvider.getCredential(idToken, null)
        var firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signInWithCredential(crd).addOnSuccessListener { res ->
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener { error ->
            Toast.makeText(this, "${error.message}", Toast.LENGTH_LONG).show()
            Log.e("TAG", "fireToken: ${error.message}")
        }

    }


    fun signingAccount(email: String, password: String) {
        var firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener { res ->
            Toast.makeText(this, "Login SuccessFully", Toast.LENGTH_LONG).show()

//            logInScreenBinding.Progressbar.isVisible = false
//            logInScreenBinding.logIn.isVisible = true

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }.addOnFailureListener { error ->
            Toast.makeText(this, "${error.message}", Toast.LENGTH_LONG).show()
//            binding.signingTxt.isVisible = true
//            binding.Progressbar.isVisible = false
        }

    }

}