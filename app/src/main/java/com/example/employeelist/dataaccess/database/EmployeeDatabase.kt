package com.example.employeelist.dataaccess.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.employeelist.dataaccess.database.dao.EmployeeDao
import com.example.employeelist.dataaccess.database.entity.AddressEntity
import com.example.employeelist.dataaccess.database.entity.EmployeeEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [EmployeeEntity::class, AddressEntity::class], version = 1)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}