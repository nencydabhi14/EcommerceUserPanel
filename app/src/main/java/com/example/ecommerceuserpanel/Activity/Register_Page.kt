package com.example.ecommerceuserpanel.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth

class Register_Page : AppCompatActivity() {

    lateinit var registerPageBinding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        registerPageBinding = ActivityRegisterPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(registerPageBinding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.rama)

        registerPageBinding.createAccountTxt.setOnClickListener {
            createAccount(
                registerPageBinding.emailEdt.text.toString(),
                registerPageBinding.passwordEdt.text.toString()
            )
            registerPageBinding.Progressbar.isVisible = true
            registerPageBinding.createAccountTxt.isVisible = false
        }

    }
    fun createAccount(email: String, password: String) {
        var firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener { res ->
            Toast.makeText(this, "Your Account create Successfully", Toast.LENGTH_LONG).show()
            registerPageBinding.Progressbar.isVisible = false
            registerPageBinding.createAccountTxt.isVisible = true
            finish()
        }.addOnFailureListener { error ->
            Toast.makeText(this, "${error.message}", Toast.LENGTH_LONG).show()
            registerPageBinding.createAccountTxt.isVisible = true
            registerPageBinding.Progressbar.isVisible = false
        }
    }

}