package com.example.ecommerceuserpanel.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceuserpanel.Adaptor.FavoritProductAaptor
import com.example.ecommerceuserpanel.DataClass.AddToFavoritModel
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.ActivityAllFavoriteScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllFavorite_Screen : AppCompatActivity() {

    lateinit var allFavoriteScreenBinding: ActivityAllFavoriteScreenBinding

    var listf = arrayListOf<AddToFavoritModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        allFavoriteScreenBinding = ActivityAllFavoriteScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(allFavoriteScreenBinding.root)
        ReadFavoritData()


        window.statusBarColor = ContextCompat.getColor(this, R.color.rama)

        allFavoriteScreenBinding.backAllLikeproduct.setOnClickListener {
            onBackPressed()
        }

        allFavoriteScreenBinding.viewProductMain.setOnClickListener {
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun ReadFavoritData() {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var reference = firebaseDatabase.reference

        reference.child("AddToFavorite").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listf.clear()
                for (x in snapshot.children) {
                    var pname = x.child("pname").getValue().toString()
                    var pprice = x.child("pprice").getValue().toString()
                    var pcategory = x.child("pcategory").getValue().toString()
                    var pdiscription = x.child("pdiscription").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var key = x.key.toString()
                    var poffer = x.child("poffer").getValue().toString()

                    var m1 = AddToFavoritModel(
                        pname,
                        pprice, image,
                        pdiscription,
                        pcategory, key,poffer
                    )
                    listf.add(m1)
                }
                SetUpRv(listf)

                if (listf.size.equals(0)) {
                    allFavoriteScreenBinding.hertImg.isVisible = true
                    allFavoriteScreenBinding.curverdArrow.isVisible = true
                    allFavoriteScreenBinding.emptyTxtFvt.isVisible = true
                    allFavoriteScreenBinding.txtx2.isVisible = true
                    allFavoriteScreenBinding.txtx3.isVisible = true
                }
                else {
                    allFavoriteScreenBinding.hertImg.isVisible = false
                    allFavoriteScreenBinding.curverdArrow.isVisible = false
                    allFavoriteScreenBinding.emptyTxtFvt.isVisible = false
                    allFavoriteScreenBinding.txtx2.isVisible = false
                    allFavoriteScreenBinding.txtx3.isVisible = false
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "onCancelled: ${error.message}")
            }
        })
    }

    fun SetUpRv(listf: ArrayList<AddToFavoritModel>) {
        var favoritProductAaptor = FavoritProductAaptor(this, listf)
        var lm = LinearLayoutManager(this)
        allFavoriteScreenBinding.favoriteRecyclerView.layoutManager = lm
        allFavoriteScreenBinding.favoriteRecyclerView.adapter = favoritProductAaptor
    }

}