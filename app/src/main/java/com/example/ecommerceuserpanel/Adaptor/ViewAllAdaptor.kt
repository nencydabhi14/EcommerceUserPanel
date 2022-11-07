package com.example.ecommerceuserpanel.Adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceuserpanel.Activity.Details_Screen
import com.example.ecommerceuserpanel.Activity.ViewAll_Screen
import com.example.ecommerceuserpanel.DataClass.ModelDataProduct
import com.example.ecommerceuserpanel.R

class ViewAllAdaptor(val viewallScreen: ViewAll_Screen, val list: ArrayList<ModelDataProduct>) :
    RecyclerView.Adapter<ViewAllAdaptor.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img_all = itemView.findViewById<ImageView>(R.id.img_all)
        var product_name_all = itemView.findViewById<TextView>(R.id.product_name_all)
        var product_price = itemView.findViewById<TextView>(R.id.product_price)
        var product_category_all = itemView.findViewById<TextView>(R.id.product_category_all)

        var details_screen = itemView.findViewById<RelativeLayout>(R.id.details_screen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(viewallScreen).inflate(R.layout.viewall_item, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.product_name_all.text = list[position].pname
        holder.product_price.text = list[position].pprice
        holder.product_category_all.text = list[position].pcategory

        Glide.with(viewallScreen).load(list[position].image)
            .placeholder(R.drawable.ic_launcher_background).into(holder.img_all)

        holder.details_screen.setOnClickListener {
            var intent = Intent(viewallScreen, Details_Screen::class.java)
            intent.putExtra("pname",list[position].pname)
            intent.putExtra("pprice",list[position].pprice)
            intent.putExtra("image",list[position].image)
            intent.putExtra("pdiscription",list[position].pdiscription)
            intent.putExtra("pcategory",list[position].pcategory)
            intent.putExtra("pkey",list[position].key)
            intent.putExtra("poffer",list[position].poffer)

            viewallScreen.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}