package com.example.employeelist.ui.employeeList

import android.util.Log
import com.example.employeelist.domain.GetAllEmployees
import com.example.employeelist.domain.RemoveEmployee
import com.example.employeelist.entity.Employee
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class EmployeeListViewModel @Inject constructor(
    private val getAllEmployees: GetAllEmployees,
    private val removeEmployee: RemoveEmployee
) {

    private val onClearDisposable = CompositeDisposable()
    private val employeeListItemsSubject = BehaviorSubject.create<List<Employee>>()
    val employeeListItemsStream: Observable<List<Employee>>
        get() = employeeListItemsSubject.observeOn(AndroidSchedulers.mainThread())

    init {
        getEmployeeList()
    }

    private fun getEmployeeList() {
        getAllEmployees.execute()
            .subscribeOn(Schedulers.io())
            .subscribe({
                employeeListItemsSubject.onNext(it)
            }, {
                Log.e(LOG_TAG, "Error during fething employee lists")
            })
            .addTo(onClearDisposable)
    }

    fun removeEmployee(employee: Employee) {
        removeEmployee.execute(employee)
            .subscribe()
            .addTo(onClearDisposable)
    }

    fun filterAirports(text: String){

    }

    companion object {
        private val LOG_TAG = EmployeeListViewModel::class.java.simpleName
    }

}