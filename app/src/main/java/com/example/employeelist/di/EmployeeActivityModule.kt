package com.example.employeelist.di

import com.example.employeelist.ui.employee.AddAddressFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EmployeeActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributesDialogFragment(): AddAddressFragment
}