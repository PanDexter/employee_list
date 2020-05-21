package com.example.employeelist.domain

import com.example.employeelist.dataaccess.repository.EmployeeRepository
import javax.inject.Inject

class GetAllEmployees @Inject constructor(private val repository: EmployeeRepository) {

    fun execute() = repository.getAllEmployees()
}