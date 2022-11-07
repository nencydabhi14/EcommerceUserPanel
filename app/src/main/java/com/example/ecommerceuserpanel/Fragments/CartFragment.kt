package com.example.ecommerceuserpanel.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceuserpanel.Activity.MainActivity
import com.example.ecommerceuserpanel.Activity.ViewAll_Screen
import com.example.ecommerceuserpanel.Adaptor.AllProductOfferAdaptor
import com.example.ecommerceuserpanel.Adaptor.CartProductAdaptor
import com.example.ecommerceuserpanel.DataClass.AddToCartRead
import com.example.ecommerceuserpanel.DataClass.ModelDataProduct
import com.example.ecommerceuserpanel.DataClass.ModelDataRead
import com.example.ecommerceuserpanel.databinding.FragmentCartBinding
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment(mainActivity: MainActivity) : Fragment() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var fragmentCartBinding: FragmentCartBinding

        var list = arrayListOf<AddToCartRead>()
        var listoffer = arrayListOf<ModelDataRead>()

        var pricetotal = 0
        var totaldiscount = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCartBinding = FragmentCartBinding.inflate(layoutInflater, container, false)

        finish()
        Medium_Rectangle()
        AddToCartRead1()
        ReadDataAll_offer_PRoduct()

        fragmentCartBinding.viewProduct.setOnClickListener {
            var intent = Intent(activity, ViewAll_Screen::class.java)
            startActivity(intent)
            finish()
        }
        return fragmentCartBinding.root
    }


    private fun Medium_Rectangle() {
        var adView =
            AdView(activity, "1148761029317477_1148769445983302", AdSize.RECTANGLE_HEIGHT_250)
        fragmentCartBinding.addsRectangle.addView(adView)
        adView.loadAd()
    }

    fun finish() {
        fragmentCartBinding.backCart.setOnClickListener {
            finish()
        }
    }

    fun AddToCartRead1() {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference = firebaseDatabase.reference

        reference.child("AddToCart").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (x in snapshot.children) {
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var pcategory = x.child("pcategory").getValue().toString()
                    var pdiscription = x.child("pdiscription").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var key = x.key.toString()
                    var pquantity = x.child("pquantity").getValue().toString()
                    var poffer = x.child("poffer").getValue().toString()
                    var totaloffer = x.child("totaloffer").getValue().toString()

                    var m1 = AddToCartRead(
                        pname,
                        pprice,
                        pcategory!!,
                        pdiscription, image, key, pquantity, poffer,totaloffer
                    )
                    list.add(m1)

                    Log.e(
                        "TAG",
                        "onDataChange========================================================: $pname $pprice $pcategory $pdiscription $key $image"
                    )
                }

                TotalPriceCount()
                setUpRV(list)

                fragmentCartBinding.totalItems.text = list.size.toString()
                fragmentCartBinding.totalCart.text = list.size.toString()

                if (list.size.equals(0)) {
                    fragmentCartBinding.emptyCartImg.isVisible = true
                    fragmentCartBinding.emptyTxt.isVisible = true
                    fragmentCartBinding.viewProduct.isVisible = true

                    fragmentCartBinding.billing.isVisible = false
                    fragmentCartBinding.addsRectangle.isVisible = false
                    fragmentCartBinding.circle.isVisible = false
                    fragmentCartBinding.totalCart.isVisible = false
                } else {
                    fragmentCartBinding.emptyCartImg.isVisible = false
                    fragmentCartBinding.emptyTxt.isVisible = false
                    fragmentCartBinding.viewProduct.isVisible = false

                    fragmentCartBinding.billing.isVisible = true
                    fragmentCartBinding.addsRectangle.isVisible = true
                    fragmentCartBinding.circle.isVisible = true
                    fragmentCartBinding.totalCart.isVisible = true
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "onCancelled: error")
            }
        })
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
                    var pcategory = x.child("category").getValue().toString()
                    var pdiscription = x.child("discription").getValue().toString()
                    var poffer = x.child("pofferEdt").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var categoryId = x.child("categoryId").getValue().toString()
                    var key = x.key.toString()
                    val totaloffer = x.child("totaloffer").getValue().toString()
                    var m1 = ModelDataRead(
                        pname,
                        pprice,
                        pcategory,
                        pdiscription,
                        poffer,
                        image,
                        key,
                        categoryId,totaloffer
                    )
                    listoffer.add(m1)
                    Log.e(
                        "TAG",
                        "onDataChange=========: $pname $pprice $poffer $pcategory $categoryId"
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "onCancelled: error")
            }
        })
    }


    fun setUpRV(list: ArrayList<AddToCartRead>) {
        var cartProductAdaptor = CartProductAdaptor(activity, list)
        var lm = LinearLayoutManager(activity)
        fragmentCartBinding.addtocartRecyclerview.layoutManager = lm
        fragmentCartBinding.addtocartRecyclerview.adapter = cartProductAdaptor
    }

    fun TotalPriceCount() {
        pricetotal = 0
        var t = 0
        totaldiscount = 0

        var i = 0
        while (i < list.size) {

            pricetotal += list[i].pprice.toInt() * list[i].pquantity!!.toInt()

            if (list[i].poffer.equals("")) {
//                pricetotal += list[i].pprice.toInt() * list[i].pquantity!!.toInt()
            } else {
                t += list[i].pprice.toInt() * list[i].pquantity!!.toInt()

//                totaldiscount =  list[i].totaloffer!!.toInt() * list[i].pquantity!!.toInt()

                totaldiscount = (t * list[i].poffer!!.toInt())/100
            }
            i++
        }
        fragmentCartBinding.totalAmountTxt.text = pricetotal.toString()
        fragmentCartBinding.totalPrice.text =
            (pricetotal.toInt() - totaldiscount.toInt()).toString()

        fragmentCartBinding.totalDiscount.text = totaldiscount.toString()

    }
}