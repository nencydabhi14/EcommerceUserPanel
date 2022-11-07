package com.example.ecommerceuserpanel.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceuserpanel.Adaptor.ShowCProductAdaptor
import com.example.ecommerceuserpanel.Adaptor.ShowCategoryAdaptor
import com.example.ecommerceuserpanel.DataClass.ModelCategoy
import com.example.ecommerceuserpanel.DataClass.ModelDataProduct
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.ActivityCategoryScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Category_Screen : AppCompatActivity() {

    lateinit var categoryScreenBinding: ActivityCategoryScreenBinding

    var list1 = arrayListOf<ModelCategoy>()
    var stringList = arrayListOf<String>()
    var data = arrayOf<String>()
    var category: String? = null

    var listproduct = arrayListOf<ModelDataProduct>()

    var category_img = arrayOf(
        R.drawable.womenethnic,
        R.drawable.womenwestern,
        R.drawable.womentopwere,
        R.drawable.menbottomwere,
        R.drawable.womensleepwere,
        R.drawable.womensportwere,
        R.drawable.mentopwere,
        R.drawable.menbottomwere,
        R.drawable.menaccessories,
        R.drawable.menfootwere,
        R.drawable.menethenic,
        R.drawable.mensleepwere,
        R.drawable.kidswere,
        R.drawable.kidstoy,
        R.drawable.babycare,
        R.drawable.kichenaccessories,
        R.drawable.hometexttiles,
        R.drawable.homedecore,
        R.drawable.mackup,
        R.drawable.perconalcare,
        R.drawable.healthcare,
        R.drawable.jewellery,
        R.drawable.womenbag,
        R.drawable.menbag,
        R.drawable.travelbag,
        R.drawable.womenfootwere,
        R.drawable.kidsfootwere,
        R.drawable.audio,
        R.drawable.mobileaccessories,
        R.drawable.smartwatches,
        R.drawable.bsaccessories,
        R.drawable.food,
        R.drawable.dink,
        R.drawable.book
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        categoryScreenBinding = ActivityCategoryScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(categoryScreenBinding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.rama)

        backCategorypage()
        readData()
        ReadDataAllPRoduct("1")
    }

    private fun backCategorypage() {
        categoryScreenBinding.backCategory.setOnClickListener {
            finish()
        }
    }

    private fun readData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        databaseReference.child("category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list1.clear()
                stringList.clear()
                data = emptyArray()
                for (x in snapshot.children) {

                    var id = x.child("id").getValue().toString()
                    category = x.child("category").getValue().toString()

                    var mc1 = ModelCategoy(id, category!!)

                    list1.add(mc1)
                    data += x.child("category").getValue().toString()

                    setUpCategory(list1)

                    Log.e("TAG", "onDataChangeCategory: ${mc1.category}")

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Category_Screen, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setUpCategory(list1: ArrayList<ModelCategoy>) {
        var adaptor = ShowCategoryAdaptor(this, list1, category_img)
        var lm = LinearLayoutManager(this)
        categoryScreenBinding.categoryRecyclerView.adapter = adaptor
        categoryScreenBinding.categoryRecyclerView.layoutManager = lm
    }

    fun setUpRV(listproduct: ArrayList<ModelDataProduct>) {
        var adaptor = ShowCProductAdaptor(this, listproduct)
        var lm = GridLayoutManager(this,3)
        categoryScreenBinding.allCategoryProduct.adapter = adaptor
        categoryScreenBinding.allCategoryProduct.layoutManager = lm
    }

    fun ReadDataAllPRoduct(cateid: String) {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference = firebaseDatabase.reference

        reference.child("Admin").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listproduct.clear()
                for (x in snapshot.children) {
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var pcategory = x.child("pcategory").getValue().toString()
                    var pdiscription = x.child("pdiscription").getValue().toString()
                    var poffer = x.child("poffer").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var offer = x.child("offer").getValue().toString()
                    var categoryId = x.child("categoryId").getValue().toString()
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
                    if (categoryId.equals(cateid)){
                        listproduct.add(m1)
                    }

                    Log.e(
                        "TAG",
                        "onDataChange========================================================: $pname $pprice $pcategory $pdiscription $poffer $key $image ,$offer"
                    )
                }
                setUpRV(listproduct)
                categoryScreenBinding.progressBar.isVisible = false
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "onCancelled: error")
            }
        })
    }


}