package com.example.employeelist.di

import com.example.employeelist.ui.employee.EmployeeActivity
import com.example.employeelist.ui.employeeList.EmployeeListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindEmployeeListActivity(): EmployeeListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [EmployeeActivityModule::class])
    abstract fun bindEmployeeActivity(): EmployeeActivity
}