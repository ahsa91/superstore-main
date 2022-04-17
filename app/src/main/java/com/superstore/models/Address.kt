package com.superstore.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
//data class for address
@Parcelize
data class Address(
    val user_id: String = "",
    val name: String = "",
    val mobileNumber: String = "",

    val address: String = "",
    val postCode: String = "",
    val additionalNote: String = "",

    val type: String = "",
    val otherDetails: String = "",
    var id: String = "",
) : Parcelable