package com.example.ecommerceuserpanel.Adaptor

import android.content.Intent
import android.media.Image
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
import com.example.ecommerceuserpanel.Activity.OfferActivity_Screen
import com.example.ecommerceuserpanel.DataClass.ModelDataRead
import com.example.ecommerceuserpanel.Fragments.HomeFragment
import com.example.ecommerceuserpanel.R

class AllProductAdaptor(val offerActivityScreen: OfferActivity_Screen,val  l1: ArrayList<ModelDataRead>) :
    RecyclerView.Adapter<AllProductAdaptor.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var product_name_offer = itemView.findViewById<TextView>(R.id.product_name_offer)
        var product_description_offer = itemView.findViewById<TextView>(R.id.product_description_offer)
        var product_price_offer = itemView.findViewById<TextView>(R.id.product_price_offer)
        var product_image_offer = itemView.findViewById<ImageView>(R.id.product_image_offer)
        var product_offer = itemView.findViewById<TextView>(R.id.product_offer)
        var details_Click = itemView.findViewById<CardView>(R.id.details_Click)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(offerActivityScreen)
            .inflate(R.layout.allptoduct_item_design, parent, false)

        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.product_name_offer.text = l1[position].pname
        holder.product_description_offer.text = l1[position].discription
        holder.product_price_offer.text = l1[position].pprice
        holder.product_offer.text = l1[position].pofferEdt

        Glide.with(offerActivityScreen).load(l1[position].image)
            .placeholder(R.drawable.ic_launcher_foreground).into(holder.product_image_offer)

        holder.details_Click.setOnClickListener {
            var intent = Intent(offerActivityScreen, Details_Screen::class.java)
            intent.putExtra("pname",l1[position].pname)
            intent.putExtra("pprice",l1[position].pprice)
            intent.putExtra("image",l1[position].image)
            intent.putExtra("pdiscription",l1[position].discription)
            intent.putExtra("pcategory",l1[position].category)
            intent.putExtra("pkey",l1[position].key)
            intent.putExtra("poffer",l1[position].pofferEdt)
            intent.putExtra("totaloffer",l1[position].totaloffer)

            offerActivityScreen.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return l1.size
    }
}