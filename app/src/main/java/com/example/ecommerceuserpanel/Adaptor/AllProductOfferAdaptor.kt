package com.example.ecommerceuserpanel.Adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceuserpanel.Activity.Details_Screen
import com.example.ecommerceuserpanel.Activity.MainActivity
import com.example.ecommerceuserpanel.DataClass.ModelDataProduct
import com.example.ecommerceuserpanel.Fragments.HomeFragment
import com.example.ecommerceuserpanel.R

class AllProductOfferAdaptor(
    val homeFragment: FragmentActivity?,
    val l1: ArrayList<ModelDataProduct>
) : RecyclerView.Adapter<AllProductOfferAdaptor.ViewData>() {

    var courseList: ArrayList<ModelDataProduct>? = null

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var product_image_R = itemView.findViewById<ImageView>(R.id.product_image_R)
        var product_name_txt = itemView.findViewById<TextView>(R.id.product_name_txt)
        var product_price_txt = itemView.findViewById<TextView>(R.id.product_price_txt)
        var product_description_txt = itemView.findViewById<TextView>(R.id.product_description_txt)
        var details_Click = itemView.findViewById<CardView>(R.id.details_Click)
        var product_catrgory_txt = itemView.findViewById<TextView>(R.id.product_catrgory_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(homeFragment)
            .inflate(R.layout.allproduct_offeritem_design, parent, false)

        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.product_name_txt.text = l1[position].pname
        holder.product_price_txt.text = l1[position].pprice
        holder.product_description_txt.text = l1[position].pdiscription
        holder.product_catrgory_txt.text = l1[position].pcategory

        Glide.with(homeFragment!!).load(l1[position].image)
            .placeholder(R.drawable.ic_launcher_background).into(holder.product_image_R)

        holder.details_Click.setOnClickListener {
            var intent = Intent(homeFragment, Details_Screen::class.java)
            intent.putExtra("pname",l1[position].pname)
            intent.putExtra("pprice",l1[position].pprice)
            intent.putExtra("image",l1[position].image)
            intent.putExtra("pdiscription",l1[position].pdiscription)
            intent.putExtra("pcategory",l1[position].pcategory)
            intent.putExtra("pkey",l1[position].key)
            intent.putExtra("poffer",l1[position].poffer)
            homeFragment.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return l1.size
    }

    fun SerchAdaptor(l1 : ArrayList<ModelDataProduct>) {
        courseList = l1
        notifyDataSetChanged()
    }
}