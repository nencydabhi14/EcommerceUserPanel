package com.example.ecommerceuserpanel.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceuserpanel.Adaptor.ViewAllAdaptor
import com.example.ecommerceuserpanel.DataClass.ModelDataProduct
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.ActivityViewAllScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewAll_Screen : AppCompatActivity() {

    lateinit var viewAllScreenBinding : ActivityViewAllScreenBinding

    var listproduct = arrayListOf<ModelDataProduct>()
    var pcategory: String? = null
    var categoryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        viewAllScreenBinding = ActivityViewAllScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewAllScreenBinding.root)

        ReadDataAllPRoduct()

        window.statusBarColor = ContextCompat.getColor(this, R.color.rama)

        viewAllScreenBinding.backAllProduct.setOnClickListener {
            onBackPressed()
        }

    }
    fun ReadDataAllPRoduct() {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference = firebaseDatabase.reference

        reference.child("Admin").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listproduct.clear()


                for (x in snapshot.children) {
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    pcategory = x.child("pcategory").getValue().toString()
                    var pdiscription = x.child("pdiscription").getValue().toString()
                    var poffer = x.child("poffer").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var offer = x.child("offer").getValue().toString()
                    categoryId = x.child("categoryId").getValue().toString()
                    var key = x.key.toString()

                    var m1 = ModelDataProduct(
                        pname,
                        pprice,
                        pcategory!!,
                        pdiscription,
                        poffer,
                        image,
                        key, offer,
                        categoryId!!
                    )
                    listproduct.add(m1)

                    Log.e(
                        "TAG",
                        "onDataChange========================================================: $pname $pprice $pcategory $pdiscription $poffer $key $image ,$offer"
                    )
                }


                setUpRV(listproduct)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "onCancelled: error")
            }
        })
    }

    fun setUpRV(list: ArrayList<ModelDataProduct>) {

        var allProductAdaptor = ViewAllAdaptor(this@ViewAll_Screen, list)
        var lm = GridLayoutManager(this@ViewAll_Screen,2)
        viewAllScreenBinding.viewAllRecycler.layoutManager = lm
        viewAllScreenBinding.viewAllRecycler.adapter = allProductAdaptor
    }


}