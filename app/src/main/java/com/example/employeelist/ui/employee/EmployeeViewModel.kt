package com.example.employeelist.ui.employee

import androidx.lifecycle.ViewModel
import com.example.employeelist.domain.SaveEmployee
import com.example.employeelist.entity.Employee
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class EmployeeViewModel @Inject constructor(
    private val saveEmployee: SaveEmployee
) : ViewModel() {

    private val onClearDisposable = CompositeDisposable()

    fun saveEmployee(employee: Employee) {
        saveEmployee.execute(employee)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(onClearDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        onClearDisposable.clear()
    }
}