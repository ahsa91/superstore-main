package com.superstore.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
//data class for the cart
@Parcelize
data class Cart(
    val user_id: String = "",
    val product_id: String = "",
    val product_owner_id: String = "",
    val title: String = "",
    val price: String = "",
    val image: String = "",
    var cart_quantity: String = "",
    var stock_quantity: String = "",
    var id: String = "",
) : Parcelable