package com.example.employeelist.dataaccess.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val addressId: Int,
    val addressWithEmployeeId: Int,
    val street: String,
    val city: String,
    val zipCode: String,
    val country: String
)

fun EmployeeWithAddress.withEmployeeId(employeeId: Int) =
    addresses.map { it.copy(addressWithEmployeeId = employeeId) }