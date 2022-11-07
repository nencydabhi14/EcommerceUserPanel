package com.example.ecommerceuserpanel.Adaptor

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceuserpanel.Activity.AllFavorite_Screen
import com.example.ecommerceuserpanel.Activity.Details_Screen
import com.example.ecommerceuserpanel.DataClass.AddToFavoritModel
import com.example.ecommerceuserpanel.R
import com.google.firebase.database.FirebaseDatabase

class FavoritProductAaptor(
    val allfavoriteScreen: AllFavorite_Screen,
    val list: ArrayList<AddToFavoritModel>
) : RecyclerView.Adapter<FavoritProductAaptor.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var product_image_fvt = itemView.findViewById<ImageView>(R.id.product_image_fvt)

        var product_name_fvt = itemView.findViewById<TextView>(R.id.product_name_fvt)
        var product_description_fvt = itemView.findViewById<TextView>(R.id.product_description_fvt)
        var product_price_fvt = itemView.findViewById<TextView>(R.id.product_price_fvt)

        var remove_form_fvt = itemView.findViewById<RelativeLayout>(R.id.remove_form_fvt)
        var details_show_fvt = itemView.findViewById<RelativeLayout>(R.id.details_show_fvt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(allfavoriteScreen)
            .inflate(R.layout.allfavorite_items, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.product_name_fvt.text = list[position].pname
        holder.product_description_fvt.text = list[position].pdiscription
        holder.product_price_fvt.text = list[position].pprice

        Glide.with(allfavoriteScreen).load(list[position].image)
            .placeholder(R.drawable.ic_launcher_foreground).into(holder.product_image_fvt)

        holder.details_show_fvt.setOnClickListener {
            var intent = Intent(allfavoriteScreen, Details_Screen::class.java)
            intent.putExtra("pname", list[position].pname)
            intent.putExtra("pprice", list[position].pprice)
            intent.putExtra("image", list[position].image)
            intent.putExtra("pdiscription", list[position].pdiscription)
            intent.putExtra("pcategory", list[position].pcategory)
            intent.putExtra("pkey", list[position].key)
            intent.putExtra("poffer",list[position].poffer)
            allfavoriteScreen.startActivity(intent)
        }

        holder.remove_form_fvt.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var fireref = firebaseDatabase.reference

            var dialogdelete = Dialog(allfavoriteScreen)
            dialogdelete.setContentView(R.layout.dialogdelete_fvt)
            dialogdelete.show()

            var yes_delete = dialogdelete.findViewById<TextView>(R.id.yes_delete)
            var pname_txt = dialogdelete.findViewById<TextView>(R.id.pname_txt)
            var cancel_delete = dialogdelete.findViewById<TextView>(R.id.cancel_delete)

            pname_txt.text = list[position].pname

            yes_delete.setOnClickListener {
                fireref.child("AddToFavorite").child(list[position].key!!).removeValue()
                dialogdelete.dismiss()
                list.clear()
            }

            cancel_delete.setOnClickListener {
                dialogdelete.dismiss()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}