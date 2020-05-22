package com.example.employeelist.ui.employeeList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeelist.R
import com.example.employeelist.databinding.ActivityEmployeeListBinding
import com.example.employeelist.entity.Employee
import com.example.employeelist.ui.employee.EmployeeActivity
import com.example.employeelist.ui.employee.EmployeeActivity.Companion.EMPLOYEE
import com.example.employeelist.ui.employeeList.recycler.EmployeeListAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class EmployeeListActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: EmployeeListViewModel
    @Inject
    lateinit var employeeAdapter: EmployeeListAdapter

    private lateinit var binding: ActivityEmployeeListBinding
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_list)
        setupRecycler()
        observeItems()
        observeItemClicks()
        observeRemoveClicks()
        filterAirports()
        onAddButtonClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun setupRecycler() {
        with(binding.rvEmployee) {
            layoutManager = LinearLayoutManager(context)
            adapter = employeeAdapter
        }
    }

    private fun observeItems() {
        viewModel.employeeListItemsStream
            .subscribe({
                employeeAdapter.setDataWithDiff(it)
            }, {
                Log.e(ERROR_TAG, "Error while observing adapter items")
            })
            .addTo(disposable)
    }

    private fun observeItemClicks() {
        employeeAdapter.selectedItemStream
            .subscribe({
                val selectedItem = employeeAdapter.items[it]
                openEmployeeActivity(selectedItem)
            }, {
                Log.e(ERROR_TAG, "Error while observing RV clicks")
            })
            .addTo(disposable)
    }

    private fun observeRemoveClicks() {
        employeeAdapter.deletedItemStream
            .subscribe({
                val selectedItem = employeeAdapter.items[it]
                viewModel.removeEmployee(selectedItem)
            }, {
                Log.e(ERROR_TAG, "Error while observing remove clicks")
            })
            .addTo(disposable)
    }

    private fun filterAirports() {
        RxTextView.textChangeEvents(binding.etFilter)
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribe({
                viewModel.filterAirports(it.text().toString())
            }, {
                Log.e(ERROR_TAG, "Error while observing edit text")
            })
            .addTo(disposable)
    }

    private fun onAddButtonClick() {
        binding.btnAdd.setOnClickListener {
            openEmployeeActivity()
        }
    }


    private fun openEmployeeActivity(employee: Employee? = null) {
        Intent(applicationContext, EmployeeActivity::class.java)
            .putExtra(EMPLOYEE, employee)
            .let {
                startActivity(it)
            }
    }


    companion object {
        private val ERROR_TAG = EmployeeListActivity::class.java.simpleName

    }

}