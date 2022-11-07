package com.example.ecommerceuserpanel.DataClass

data class AddToCartRead(
    val pname: String,
    val pprice: String,
    val pcategory: String,
    val pdiscription: String,
    val image: String,
    val key: String,
    val pquantity : String?,
    val poffer : String?,
    val totaloffer : String?
)
