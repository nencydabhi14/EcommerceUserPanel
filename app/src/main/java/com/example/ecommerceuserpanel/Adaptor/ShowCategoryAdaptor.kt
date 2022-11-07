package com.example.ecommerceuserpanel.Adaptor

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceuserpanel.Activity.Category_Screen
import com.example.ecommerceuserpanel.Adaptor.Static_Class.Companion.categoryID
import com.example.ecommerceuserpanel.DataClass.ModelCategoy
import com.example.ecommerceuserpanel.R

class ShowCategoryAdaptor(
    val categoryScreen: Category_Screen,
    val list1: ArrayList<ModelCategoy>,
    val category_img: Array<Int>,
) : RecyclerView.Adapter<ShowCategoryAdaptor.ViewData>() {

    var list = arrayListOf<Int>(
        R.color.white,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey,
        R.color.grey
    )

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category_txt = itemView.findViewById<TextView>(R.id.category_txt)
        var category_img = itemView.findViewById<ImageView>(R.id.category_img)
        var click_caregory = itemView.findViewById<RelativeLayout>(R.id.click_caregory)
        var line_cate = itemView.findViewById<RelativeLayout>(R.id.line_cate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view =
            LayoutInflater.from(categoryScreen).inflate(R.layout.all_category, parent, false)
        return ViewData(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.category_txt.text = list1[position].category

        Glide.with(categoryScreen).load(category_img[position]).placeholder(R.drawable.categoryh)
            .into(holder.category_img)

        holder.click_caregory.setBackgroundColor(categoryScreen.getColor(list[position]))
        holder.line_cate.isVisible = true

        var i = 0
        holder.click_caregory.setOnClickListener {

            categoryID = list1[position].id

            list[position] = R.color.white

            categoryScreen.ReadDataAllPRoduct(categoryID)

            holder.click_caregory.setBackgroundColor(categoryScreen.getColor(list[position]))
            while (i < list.size) {
                if (i != position) {
                    list[i] = R.color.grey
                }
                i++
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list1.size
    }

}