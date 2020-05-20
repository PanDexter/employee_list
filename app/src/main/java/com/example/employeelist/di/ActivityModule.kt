package com.example.employeelist.di

import com.example.employeelist.ui.MainActivity
import com.example.employeelist.ui.employee.EmployeeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindShoppingListFragment(): EmployeeActivity
}