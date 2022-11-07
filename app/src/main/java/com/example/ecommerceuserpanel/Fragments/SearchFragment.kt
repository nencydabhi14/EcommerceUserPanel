package com.example.ecommerceuserpanel.Fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceuserpanel.Activity.MainActivity
import com.example.ecommerceuserpanel.Adaptor.AllProductOfferAdaptor
import com.example.ecommerceuserpanel.DataClass.ModelDataProduct
import com.example.ecommerceuserpanel.R
import com.example.ecommerceuserpanel.databinding.FragmentSearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment(mainActivity: MainActivity) : Fragment() {
    lateinit var searchBinding: FragmentSearchBinding
    var temp = arrayOf<String>()
    var listproduct = arrayListOf<ModelDataProduct>()
    var pcategory: String? = null
    var categoryId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        searchBinding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        ReadDataAllPRoduct()
        return searchBinding.root
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

                    temp += pname

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
                setUpRV(listproduct)
                searchview()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "onCancelled: error")
            }
        })
    }


    fun setUpRV(l1: ArrayList<ModelDataProduct>) {
        val allProductAdaptor = AllProductOfferAdaptor(activity, l1)
        var lm = LinearLayoutManager(activity)
        searchBinding.recyclerview.layoutManager = lm
        searchBinding.recyclerview.adapter = allProductAdaptor
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
//        getMenuInflater().inflate(R.menu.menu_main, menu)
//        val searchManager = getSystemService<Any>(Context.SEARCH_SERVICE) as SearchManager?
//        val searchView: SearchView = menu.findItem(R.id.menu_search).actionView as SearchView
//        searchView.setSearchableInfo(searchManager!!.getSearchableInfo(getComponentName()))
//        return true
    }


    fun searchview() {

        val mArrayAdapter = ArrayAdapter(
            requireActivity(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            temp
        )
//        searchBinding.recyclerview.adapter = mArrayAdapter

        searchBinding.searchViewW.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                Filter(p0.toString())
                Log.e("TAG", "onTextChanged: $p0")
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
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
}