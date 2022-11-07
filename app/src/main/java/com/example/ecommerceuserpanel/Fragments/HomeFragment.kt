package com.example.ecommerceuserpanel.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceuserpanel.Activity.*
import com.example.ecommerceuserpanel.Adaptor.AllProductOfferAdaptor
import com.example.ecommerceuserpanel.DataClass.ModelDataProduct
import com.example.ecommerceuserpanel.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class HomeFragment(mainActivity: FragmentActivity) : Fragment() {

    lateinit var allProductAdaptor: AllProductOfferAdaptor
    lateinit var homeFragment: FragmentHomeBinding

    var name = arrayOf("Women Ethnic", "Women Western", "Women Top-were", "Women Bottom were", "Women Sleepwear", "Women Sportswear",
        "Men Top-were", "Men Bottom were", "Men Accessories", "Men Footwere", "Men Ethnic", "Men Sleepwear", "Kids Were",
        "Kids Toys and Accessories", "Baby Care", "Kitchen Accessories", "Home Textiles", "Home Decode", "Mack up", "Personal care",
        "Health Care", "Jewellery", "Women Bag", "Men Bag", "Travel Bag,Luggage", "Women Footwear", "Kids Footwere", "Audio",
        "Mobile and Accessories", "Smart Watches", "Bike And Scotty Accessories", "Food", "Drink", "Books")

    var listproduct = arrayListOf<ModelDataProduct>()
    var pcategory: String? = null
    var categoryId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        homeFragment = FragmentHomeBinding.inflate(layoutInflater, container, false)

        offerPage()
        category_Page()
        ViewAll_Product()
        FavoriteProduct()
        ReadDataAllPRoduct()
        ImageSlider()

        var arrayAdapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            name
        )
        homeFragment.searchView.setAdapter(arrayAdapter)

        homeFragment.searchView.setOnItemClickListener(OnItemClickListener { parent, arg1, pos, id ->
            Toast.makeText(
                activity, "${parent.adapter.getItem(pos)}", Toast.LENGTH_LONG
            ).show()

//            val fragment = HomeFragment(requireActivity())
//            val args = Bundle()

            var intent = Intent(activity, ViewAll_Screen::class.java)
//            args.putInt("position", parent.adapter.getItem(pos) as Int)
//            fragment.setArguments(args)
            startActivity(intent)
        })


        return homeFragment.root
    }

    private fun FavoriteProduct() {
        homeFragment.containerFavorite.setOnClickListener {
            var intent = Intent(activity, AllFavorite_Screen::class.java)
            startActivity(intent)
        }
    }

    fun Filter(text: String?) {
        val filteredlist: ArrayList<ModelDataProduct> = ArrayList()

        for (item in listproduct) {
            if (item.pname.toLowerCase().contains(text!!.toLowerCase())) {
                filteredlist.add(item)
            }
            if (filteredlist.isEmpty()) {
                Toast.makeText(activity, "NoData Found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun ViewAll_Product() {

        homeFragment.viewAllProduct.setOnClickListener {
            var intent = Intent(activity, ViewAll_Screen::class.java)
            startActivity(intent)
        }
    }

    fun offerPage() {
        homeFragment.containerOffer.setOnClickListener {
            var intent = Intent(activity, OfferActivity_Screen::class.java)
            startActivity(intent)
        }
    }

    fun category_Page() {
        homeFragment.containerCategory.setOnClickListener {
            var intent = Intent(activity, Category_Screen::class.java)
            startActivity(intent)
        }
    }

    fun ImageSlider() {
        homeFragment.carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageUrl = "https://cdn.techwireasia.com/wp-content/uploads/2018/05/shutterstock_335123132.jpg"
            )
        )
        list.add(
            CarouselItem(
                imageUrl = "https://i.pinimg.com/originals/29/d0/a5/29d0a5a483868188f862e35f92c84444.jpg"
            )
        )
        list.add(
            CarouselItem(
                imageUrl = "https://www.crosspostit.com/wp-content/uploads/2017/04/Starting-an-eCommerce-Store-1.jpg"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = "https://iqonic.design/wp-content/uploads/2021/06/shoppingcart.png"
            )
        )
        homeFragment.carousel.setData(list)

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
                        key,
                        offer,
                        categoryId!!
                    )
                    listproduct.add(m1)

                    Log.e(
                        "TAG",
                        "onDataChange========================================================: $pname $pprice $pcategory $pdiscription $poffer $key $image ,$offer"
                    )
                }
                homeFragment.progress.isVisible = false


                setUpRV(listproduct)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "onCancelled: error")
            }
        })
    }

    fun setUpRV(l1: ArrayList<ModelDataProduct>) {
        allProductAdaptor = AllProductOfferAdaptor(activity, l1)
        var lm = LinearLayoutManager(activity)
        homeFragment.allProductRecyclerview.layoutManager = lm
        homeFragment.allProductRecyclerview.adapter = allProductAdaptor
    }

}