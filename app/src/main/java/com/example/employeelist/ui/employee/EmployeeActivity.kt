package com.example.employeelist.ui.employee

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeelist.R
import com.example.employeelist.dataaccess.database.entity.Gender
import com.example.employeelist.databinding.ActivityEmployeeBinding
import com.example.employeelist.entity.Address
import com.example.employeelist.entity.Employee
import com.example.employeelist.ui.employee.recycler.AddressAdapter
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class EmployeeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModel: EmployeeViewModel
    @Inject
    lateinit var addressAdapter: AddressAdapter

    private lateinit var binding: ActivityEmployeeBinding
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee)
        val employee = intent.getParcelableExtra(EMPLOYEE) as Employee? ?: return
        binding.employee = employee
        setupRecycler()
        onAddAddressClick()
        onSaveClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun setupRecycler() {
        with(binding.rvAddress) {
            layoutManager = LinearLayoutManager(context)
            adapter = addressAdapter
        }
    }

    private fun observeItems() {

    }

    private fun onAddAddressClick() {
        binding.btnAdd.setOnClickListener {
            AddAddressFragment.newInstance().apply {
                setTargetFragment(this, TARGET_FRAGMENT_REQUEST_CODE)
                show(parentFragmentManager, tag)
            }
        }
    }

    private fun onSaveClick() {
        binding.btnSave.setOnClickListener {
            viewModel.saveEmployee(
                Employee(
                    employeeId = 0,
                    firstName = binding.name.text.toString(),
                    lastName = binding.lastName.text.toString(),
                    age = binding.lastName.text.toString().toInt(),
                    gender =
                    when (binding.genderGroup.checkedRadioButtonId) {
                        R.id.gender_male -> Gender.MALE
                        else -> Gender.FEMALE
                    },
                    addresses = addressAdapter.items
                )
            )
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == TARGET_FRAGMENT_REQUEST_CODE) {
            val newAddress = data?.getBundleExtra(ADDRESS) as Address
            addressAdapter.addDataWithDiff(newAddress)
        }
    }

    companion object {

        fun newAddress(address: Address) = Intent().apply {
            putExtra(ADDRESS, address)
        }

        private const val ADDRESS = "address"
        const val EMPLOYEE = "employee"
        private const val TARGET_FRAGMENT_REQUEST_CODE = 1
    }
}