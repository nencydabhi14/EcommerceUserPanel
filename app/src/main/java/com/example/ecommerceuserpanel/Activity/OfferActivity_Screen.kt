package com.example.ecommerceuserpanel.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceuserpanel.Adaptor.AllProductAdaptor
import com.example.ecommerceuserpanel.DataClass.ModelDataRead
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.ActivityOfferScreenBinding
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class OfferActivity_Screen : AppCompatActivity() {


    lateinit var offerActivityScreen: ActivityOfferScreenBinding


    var listoffer = arrayListOf<ModelDataRead>()
    var pcategory: String? = null
    var categoryId: String? = null
    var poffer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        offerActivityScreen = ActivityOfferScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(offerActivityScreen.root)
        ReadDataAll_offer_PRoduct()
        BannerAdd()
        backScreen()

        window.statusBarColor = ContextCompat.getColor(this, R.color.rama)

    }

    private fun BannerAdd() {
        var adView = AdView(this, "1148761029317477_1148763245983922", AdSize.BANNER_HEIGHT_50)
        offerActivityScreen.bannerContainer.addView(adView)
        adView.loadAd()
    }

    private fun backScreen() {
        offerActivityScreen.backOffer.setOnClickListener {
            onBackPressed()
        }
    }

    fun ReadDataAll_offer_PRoduct() {
        listoffer.clear()

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference = firebaseDatabase.reference

        reference.child("offer").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (x in snapshot.children) {
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    pcategory = x.child("category").getValue().toString()
                    var pdiscription = x.child("discription").getValue().toString()
                    poffer = x.child("pofferEdt").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    categoryId = x.child("categoryId").getValue().toString()
                    var key = x.key.toString()
                    val totaloffer = x.child("totaloffer").getValue().toString()

                    var m1 = ModelDataRead(
                        pname,
                        pprice,
                        pcategory!!,
                        pdiscription,
                        poffer!!,
                        image,
                        key,
                        categoryId!!,totaloffer
                    )
                    listoffer.add(m1)

                    Log.e(
                        "TAG",
                        "onDataChang=========: $pname $pprice $poffer $pcategory $categoryId"
                    )
//                    offerActivityScreen.progress.isVisible = false
//
//                    offerActivityScreen.seeAllofferTxt.isVisible = true
//                    offerActivityScreen.offerTxt.isVisible = true


                }
                setUpRVoffer(listoffer)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "onCancelled: error")
            }
        })
    }

    fun setUpRVoffer(l1: ArrayList<ModelDataRead>) {
        var allProductAdaptor = AllProductAdaptor(this@OfferActivity_Screen, l1)
        var lm = LinearLayoutManager(this)
        offerActivityScreen.allOfferRecyclerview.layoutManager = lm
        offerActivityScreen.allOfferRecyclerview.adapter = allProductAdaptor
    }
}