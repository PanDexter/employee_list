package com.example.employeelist.dataaccess.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    val employeeId: Int = 0,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: Gender,
    val addresses: List<String>
)

enum class Gender {
    MALE, FEMALE
}