package com.example.employeelist.entity

import android.os.Parcelable
import com.example.employeelist.dataaccess.database.entity.Gender
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employee(
    val employeeId: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: Gender,
    val addresses: List<Address>
) : Parcelable