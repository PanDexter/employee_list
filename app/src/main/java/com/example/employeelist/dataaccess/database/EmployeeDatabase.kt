package com.example.employeelist.dataaccess.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.employeelist.dataaccess.converter.GenderConverter
import com.example.employeelist.dataaccess.database.dao.EmployeeDao
import com.example.employeelist.dataaccess.database.entity.AddressEntity
import com.example.employeelist.dataaccess.database.entity.EmployeeEntity
import javax.inject.Singleton

@Singleton
@Database(entities = [EmployeeEntity::class, AddressEntity::class], version = 1)
@TypeConverters(GenderConverter::class)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}