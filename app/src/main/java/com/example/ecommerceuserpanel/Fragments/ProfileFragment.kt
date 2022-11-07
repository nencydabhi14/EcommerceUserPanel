package com.example.ecommerceuserpanel.Fragments


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.ecommerceuserpanel.Activity.LogInScreen
import com.example.ecommerceuserpanel.Activity.MainActivity
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class ProfileFragment(mainActivity: MainActivity) : Fragment() {
    lateinit var fragmentProfileBinding: FragmentProfileBinding

    var uri: Uri? = null
    var bitmap_img: Bitmap? = null

    lateinit var name: String
    lateinit var phone: String
    lateinit var email: String
    lateinit var pinocode: String
    lateinit var city: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        LogOut()

        fragmentProfileBinding.addPhotoTxt.setOnClickListener {
            imageDialog()
        }

        fragmentProfileBinding.clearDetails.setOnClickListener {
            fragmentProfileBinding.nameEdt.setText("")
            fragmentProfileBinding.phoneEdt.setText("")
            fragmentProfileBinding.emailEdt.setText("")
            fragmentProfileBinding.pincodeEdt.setText("")
            fragmentProfileBinding.cityEdt.setText("")


            Toast.makeText(
                activity,
                "Please Click right button to Remove Details",
                Toast.LENGTH_SHORT
            ).show()

        }

        fragmentProfileBinding.doneImg.setOnClickListener {


            fragmentProfileBinding.process.isVisible = true


            name = fragmentProfileBinding.nameEdt.text.toString()
            phone = fragmentProfileBinding.phoneEdt.text.toString()
            email = fragmentProfileBinding.emailEdt.text.toString()
            pinocode = fragmentProfileBinding.pincodeEdt.text.toString()
            city = fragmentProfileBinding.cityEdt.text.toString()

            var sharedPref =
                requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
            var editor = sharedPref.edit()
            editor.putString("name", name)
            editor.putString("phone", phone)
            editor.putString("email", email)
            editor.putString("pinocode", pinocode)
            editor.putString("city", city)
            editor.commit()
            Handler(Looper.getMainLooper()).postDelayed({
                fragmentProfileBinding.process.isVisible = false
                Toast.makeText(activity, "Successfully Save your Details", Toast.LENGTH_SHORT)
                    .show()
            }, 3400)
        }
        return fragmentProfileBinding.root
    }

    override fun onResume() {


        var sharedPref =
            requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        var image = sharedPref.getString("image", bitmap_img?.toString())
        var name = sharedPref.getString("name", null)
        var phone = sharedPref.getString("phone", null)
        var email = sharedPref.getString("email", null)
        var pincode = sharedPref.getString("pinocode", null)
        var city = sharedPref.getString("city", null)

        Log.e("TAG", "onResume: $image")

        fragmentProfileBinding.profileImg.isVisible = true
        Glide.with(this).load("$image").into(fragmentProfileBinding.profileImg)

        fragmentProfileBinding.nameEdt.setText(name)
        fragmentProfileBinding.phoneEdt.setText(phone)
        fragmentProfileBinding.emailEdt.setText(email)
        fragmentProfileBinding.pincodeEdt.setText(pincode)
        fragmentProfileBinding.cityEdt.setText(city)

        super.onResume()
    }


    private fun LogOut() {
        fragmentProfileBinding.logOut.setOnClickListener {
            var firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            logoutAccountGoggle()

            var intent = Intent(activity, LogInScreen::class.java)
            startActivity(intent)
        }
    }

    fun logoutAccountGoggle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        googleSignInClient.signOut()
    }

    fun imageDialog() {
        var dialogimage = BottomSheetDialog(requireActivity())
        dialogimage.setContentView(R.layout.choose_image_dialog)

        var close_dialog = dialogimage.findViewById<ImageView>(R.id.close_dialog)
        var camera_relative = dialogimage.findViewById<RelativeLayout>(R.id.camera_relative)
        var gallery_relative = dialogimage.findViewById<RelativeLayout>(R.id.gallery_relative)


        gallery_relative!!.setOnClickListener {
            var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)

            dialogimage.dismiss()
        }

        camera_relative!!.setOnClickListener {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 200)
            dialogimage.dismiss()
        }

        close_dialog!!.setOnClickListener {
            dialogimage.dismiss()
        }
        dialogimage.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            uri = data!!.data!!
            fragmentProfileBinding.profileImg.setImageURI(uri)

            fragmentProfileBinding.profileImg.isVisible = true
            fragmentProfileBinding.userProfile.isVisible = false

        } else if (requestCode == 200) {
            fragmentProfileBinding.profileImg.isVisible = true
            bitmap_img = data?.extras!!["data"] as Bitmap
            fragmentProfileBinding.profileImg.setImageBitmap(bitmap_img)

            Glide.with(this).load(bitmap_img).placeholder(R.drawable.babycare)
                .into(fragmentProfileBinding.profileImg)

            fragmentProfileBinding.userProfile.isVisible = false
        }

        var sharedPref =
            requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putString("image", uri.toString())
        editor.commit()
    }

}