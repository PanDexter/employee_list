package com.example.employeelist.di

import androidx.room.Room
import com.example.employeelist.App
import com.example.employeelist.dataaccess.database.EmployeeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun providesItemListDao(database: EmployeeDatabase) = database.employeeDao()

        @Provides
        @JvmStatic
        @Singleton
        fun providesDatabase(app: App): EmployeeDatabase = Room.databaseBuilder(
            app.applicationContext,
            EmployeeDatabase::class.java,
            "employeedb"
        ).build()
    }
}