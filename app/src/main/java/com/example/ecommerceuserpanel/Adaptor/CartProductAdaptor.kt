package com.example.ecommerceuserpanel.Adaptor

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerceuserpanel.Activity.Details_Screen
import com.example.ecommerceuserpanel.DataClass.AddToCartRead
import com.example.ecommerceuserpanel.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase

class CartProductAdaptor(val fragCart: FragmentActivity?, val list: ArrayList<AddToCartRead>) :
    RecyclerView.Adapter<CartProductAdaptor.ViewData>() {

    var qty: Int = 1
    var total_Amount: Int? = null

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var product_image_cart = itemView.findViewById<ImageView>(R.id.product_image_cart)
        var offer_img = itemView.findViewById<ImageView>(R.id.offer_img)
        var product_name_cart = itemView.findViewById<TextView>(R.id.product_name_cart)
        var off_txt = itemView.findViewById<TextView>(R.id.off_txt)
        var product_description_cart = itemView.findViewById<TextView>(R.id.product_description_cart)
        var product_price_cart = itemView.findViewById<TextView>(R.id.product_price_cart)
        var total_Qty_cart = itemView.findViewById<TextView>(R.id.total_Qty_cart)
        var product_offer_txt = itemView.findViewById<TextView>(R.id.product_offer_txt)
        var remove_form_cart = itemView.findViewById<RelativeLayout>(R.id.remove_form_cart)
        var details_show_cart = itemView.findViewById<RelativeLayout>(R.id.details_show_cart)
        var add_quantity = itemView.findViewById<RelativeLayout>(R.id.add_quantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view = LayoutInflater.from(fragCart)
            .inflate(R.layout.allcart_items, parent, false)

        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.product_name_cart.text = list[position].pname
        holder.product_description_cart.text = list[position].pdiscription
        holder.product_price_cart.text = list[position].pprice
        holder.total_Qty_cart.text = list[position].pquantity
        holder.product_offer_txt.text = list[position].poffer


        if (list[position].poffer.equals("")) {
            holder.product_offer_txt.text = "Currently unavailable offer"
            holder.offer_img.isVisible = false
            holder.off_txt.isVisible = false
            holder.product_offer_txt.setTextColor(Color.parseColor("#184040"))
        }

        Glide.with(fragCart!!).load(list[position].image)
            .placeholder(R.drawable.ic_launcher_foreground).into(holder.product_image_cart)

        holder.details_show_cart.setOnClickListener {
            var intent = Intent(fragCart, Details_Screen::class.java)
            intent.putExtra("pname", list[position].pname)
            intent.putExtra("pprice", list[position].pprice)
            intent.putExtra("image", list[position].image)
            intent.putExtra("pdiscription", list[position].pdiscription)
            intent.putExtra("pcategory", list[position].pcategory)
            intent.putExtra("pkey", list[position].key)
            intent.putExtra("poffer", list[position].poffer)
            intent.putExtra("totaloffer", list[position].totaloffer)
            fragCart.startActivity(intent)
        }

        holder.remove_form_cart.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var fireref = firebaseDatabase.reference

            var dialogdelete = Dialog(fragCart)
            dialogdelete.setContentView(R.layout.dialog_delet)
            dialogdelete.show()

            var yes_delete = dialogdelete.findViewById<TextView>(R.id.yes_delete)
            var pname_txt = dialogdelete.findViewById<TextView>(R.id.pname_txt)
            var cancel_delete = dialogdelete.findViewById<TextView>(R.id.cancel_delete)

            pname_txt.text = list[position].pname

            yes_delete.setOnClickListener {
                fireref.child("AddToCart").child(list[position].key).removeValue()
                dialogdelete.dismiss()
            }

            cancel_delete.setOnClickListener {
                dialogdelete.dismiss()
            }
        }

        holder.add_quantity.setOnClickListener {
            QuantityDialog(position)
        }

//        var p = list[position].totaloffer!!.toInt() * list[position].pquantity!!.toInt()

//        Toast.makeText(fragCart, "$p", Toast.LENGTH_SHORT).show()

    }

    private fun QuantityDialog(position: Int) {
        var dialogquantity = BottomSheetDialog(fragCart!!)
        dialogquantity.setContentView(R.layout.dialog_quantity)

        var product_image_quantity =
            dialogquantity.findViewById<ImageView>(R.id.product_image_quantity)
        var product_description_quantity =
            dialogquantity.findViewById<TextView>(R.id.product_description_quantity)
        var product_price_quantity =
            dialogquantity.findViewById<TextView>(R.id.product_price_quantity)
        var minus_card = dialogquantity.findViewById<CardView>(R.id.minus_card)
        var plus_card = dialogquantity.findViewById<CardView>(R.id.plus_card)
        var totalqty = dialogquantity.findViewById<TextView>(R.id.totalqty)
        var total_amount_qty = dialogquantity.findViewById<TextView>(R.id.total_amount_qty)
        var continue_relative = dialogquantity.findViewById<RelativeLayout>(R.id.continue_relative)

        product_description_quantity!!.text = list[position].pdiscription
        product_price_quantity!!.text = list[position].pprice
        total_amount_qty!!.text = (list[position].pprice.toInt() * list[position].pquantity!!.toInt()).toString()
        Glide.with(fragCart!!).load(list[position].image)
            .placeholder(R.drawable.ic_launcher_foreground).into(product_image_quantity!!)

        qty = list.get(position).pquantity!!.toInt()
        totalqty!!.text = list.get(position).pquantity

        minus_card!!.setOnClickListener {
            if (qty > 1) {
                qty--

                totalqty!!.text = qty.toString()

                total_Amount = qty * list[position].pprice.toInt()
                total_amount_qty.text = total_Amount.toString()

            }
        }

        plus_card!!.setOnClickListener {
            if (qty <10) {
                qty ++

                totalqty!!.text = qty.toString()

                total_Amount = qty * list[position].pprice.toInt()
                total_amount_qty.text = total_Amount.toString()
            }
        }

        continue_relative!!.setOnClickListener {
            insert(position, qty)
            dialogquantity.dismiss()
        }

        dialogquantity.show()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun insert(position: Int, qty: Int) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var cartData = AddToCartRead(
            list[position].pname,
            list[position].pprice,
            list[position].pcategory,
            list[position].pdiscription,
            list[position].image,
            list[position].key,
            qty.toString(),
            list[position].poffer,
            list[position].totaloffer
        )
        databaseReference.child("AddToCart").child(list[position].key).setValue(cartData)

    }
}