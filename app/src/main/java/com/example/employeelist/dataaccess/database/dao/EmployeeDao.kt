package com.example.employeelist.dataaccess.database.dao

import androidx.room.*
import com.example.employeelist.dataaccess.database.entity.AddressEntity
import com.example.employeelist.dataaccess.database.entity.EmployeeEntity
import com.example.employeelist.dataaccess.database.entity.EmployeeWithAddress
import com.example.employeelist.dataaccess.database.entity.withEmployeeId
import io.reactivex.Flowable

@Dao
abstract class EmployeeDao {

    @Transaction
    @Query("SELECT * FROM EmployeeEntity")
    abstract fun getAllEmployees(): Flowable<List<EmployeeWithAddress>>

    @Transaction
    @Query("SELECT * FROM EmployeeEntity WHERE employeeId =:employeeId")
    abstract fun getEmployeeById(employeeId: Int): EmployeeWithAddress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEmployee(item: EmployeeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAddresses(item: List<AddressEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAddress(item: AddressEntity)

    @Update
    abstract fun update(employeeEntity: EmployeeEntity)

    @Update
    abstract fun update(addressEntity: AddressEntity)

    @Delete
    abstract fun delete(employeeEntity: EmployeeEntity)

    @Delete
    abstract fun delete(addressEntity: AddressEntity)

    @Transaction
    open fun insertEmployeeWithAddress(item: EmployeeWithAddress) {
        val employee = getEmployeeById(item.employee.employeeId)
        if (employee == null) {
            with(insertEmployee(item.employee)) {
                insertAddresses(item.withEmployeeId(this.toInt()))
            }
        } else {
            update(item.employee)
            item.addresses.forEach {
                if (it.addressId != 0) {
                    update(it)
                } else {
                    insertAddress(it)
                }
            }
        }
    }

}