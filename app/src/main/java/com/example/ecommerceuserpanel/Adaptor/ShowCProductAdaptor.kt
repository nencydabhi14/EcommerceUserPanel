package com.example.ecommerceuserpanel.Adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceuserpanel.Activity.Category_Screen
import com.example.ecommerceuserpanel.DataClass.ModelDataProduct
import com.example.ecommerceuserpanel.R

class ShowCProductAdaptor(
    val categoryScreen: Category_Screen,
    val listproduct: ArrayList<ModelDataProduct>
) : RecyclerView.Adapter<ShowCProductAdaptor.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname_txt = itemView.findViewById<TextView>(R.id.pname_txt)
        var product_img = itemView.findViewById<ImageView>(R.id.product_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view =
            LayoutInflater.from(categoryScreen).inflate(R.layout.all_c_product, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.pname_txt.text = listproduct[position].pname

        Glide.with(categoryScreen).load(listproduct[position].image).into(holder.product_img)
    }

    override fun getItemCount(): Int {
        return listproduct.size
    }
}