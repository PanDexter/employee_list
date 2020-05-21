package com.example.employeelist.domain

import com.example.employeelist.dataaccess.repository.EmployeeRepository
import com.example.employeelist.entity.Employee
import javax.inject.Inject

class RemoveEmployee @Inject constructor(private val repository: EmployeeRepository) {

    fun execute(employee: Employee) = repository.removeEmployee(employee)
}