package com.example.ecommerceuserpanel.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.ecommerceuserpanel.Adaptor.FavoritProductAaptor
import com.example.ecommerceuserpanel.DataClass.AddToCartModel
import com.example.ecommerceuserpanel.DataClass.AddToFavoritModel
import com.example.ecommerceuserpanel.Fragments.CartFragment
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.ActivityAllFavoriteScreenBinding.inflate
import com.example.ecommerceuserpanel.databinding.ActivityDetailsScreenBinding
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase


class Details_Screen : AppCompatActivity() {

    var key: String? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var detailsScreenBinding: ActivityDetailsScreenBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        detailsScreenBinding = ActivityDetailsScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(detailsScreenBinding.root)

        RectangleAdd()

        detailsScreenBinding.backDetailsScreen.setOnClickListener {
            onBackPressed()
        }

        detailsScreenBinding.likeList.setOnClickListener {
            var intent = Intent(this, AllFavorite_Screen::class.java)
            startActivity(intent)
        }
        detailsScreenBinding.cartList.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        window.statusBarColor = ContextCompat.getColor(this, R.color.rama)

        var pname = intent.getStringExtra("pname")
        var pprice = intent.getStringExtra("pprice")
        var image = intent.getStringExtra("image")
        var pdiscription = intent.getStringExtra("pdiscription")
        var pcategory = intent.getStringExtra("pcategory")
        key = intent.getStringExtra("pkey")
        var poffer = intent.getStringExtra("poffer")
        var totaloffer = intent.getStringExtra("totaloffer")

        detailsScreenBinding.productName.text = pname
        detailsScreenBinding.productPrice.text = pprice
        detailsScreenBinding.productDescription.text = pdiscription
        detailsScreenBinding.catgroyTxt.text = pcategory

        Glide.with(this).load(image).into(detailsScreenBinding.productImg)

        var clicked = false
        detailsScreenBinding.likeDetailsImg.setImageResource(R.drawable.like)

        detailsScreenBinding.likeRelative.setOnClickListener {
            if (clicked) {
                clicked = false
                detailsScreenBinding.likeDetailsImg.setImageResource(R.drawable.like)
            } else {
                clicked = true
                detailsScreenBinding.likeDetailsImg.setImageResource(R.drawable.favorite)

                var atfvt = AddToFavoritModel(
                    pname, pprice, image, pdiscription, pcategory, key,poffer
                )
                AddToFavorite(atfvt)

                var sharedPref = this.getSharedPreferences("MyPref", MODE_PRIVATE)
                var editor = sharedPref.edit()
                editor.putInt("image", R.drawable.favorite.toInt())
                editor.commit()
            }
        }

        detailsScreenBinding.addToCart.setOnClickListener {
            var atc1 = AddToCartModel(
                pname, pprice, image, pdiscription, pcategory,"1",poffer,totaloffer
            )
            AddToCart(atc1)

            val snackBar = Snackbar.make(
                it, "Added To Cart SuccessFully..!!",
                Snackbar.LENGTH_LONG
            ).setAction("Action", null)
            snackBar.setActionTextColor(getResources().getColor(R.color.bblue))
            snackBar.setAnchorView(detailsScreenBinding.above)

            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(getResources().getColor(R.color.bblue))
            val textView =
                snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(getResources().getColor(R.color.rama))
            snackBar.show()
        }
    }

    private fun RectangleAdd() {
        var adView = AdView(this, "1148761029317477_1148769445983302", AdSize.RECTANGLE_HEIGHT_250)
        detailsScreenBinding.MediumRectangleContainer.addView(adView)
        adView.loadAd()
    }

    fun AddToCart(atc1: AddToCartModel) {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference = firebaseDatabase.reference

        reference.child("AddToCart").push().setValue(atc1)
    }

    fun AddToFavorite(atc1: AddToFavoritModel) {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference = firebaseDatabase.reference

        reference.child("AddToFavorite").push().setValue(atc1)
    }
}