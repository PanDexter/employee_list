package com.example.employeelist.ui.employee

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.employeelist.databinding.DialogAddAddressBinding
import com.example.employeelist.entity.Address
import dagger.android.support.DaggerDialogFragment

class AddAddressFragment : DaggerDialogFragment() {

    private lateinit var binding: DialogAddAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DialogAddAddressBinding.inflate(inflater).apply {
        binding = this
        onSaveButtonClick()
        onCloseButtonClick()
    }.root

    private fun onSaveButtonClick() {
        binding.btnSave.setOnClickListener {
            saveNewAddress()
        }
    }

    private fun saveNewAddress() {
        if (targetFragment == null) return
        val address = Address(
            addressId = 0,
            street = binding.street.text.toString(),
            city = binding.city.text.toString(),
            zipCode = binding.zipCode.text.toString(),
            country = binding.country.text.toString()
        )
        val intent = EmployeeActivity.newAddress(address)
        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
        dismiss()
    }

    private fun onCloseButtonClick() {
        binding.btnSave.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        fun newInstance() = AddAddressFragment()
    }

}