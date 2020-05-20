package com.example.employeelist.dataaccess.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class EmployeeWithAddress(
    @Embedded
    val employee: EmployeeEntity,
    @Relation(
        parentColumn = "employeeId",
        entityColumn = "addressWithEmployeeId"
    )
    val addresses: List<AddressEntity>
)