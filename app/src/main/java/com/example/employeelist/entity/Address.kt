package com.example.employeelist.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val addressId: Int,
    val street: String,
    val city: String,
    val zipCode: String,
    val country: String
) : Parcelable