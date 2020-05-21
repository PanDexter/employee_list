package com.example.employeelist.dataaccess.repository

import com.example.employeelist.dataaccess.database.dao.EmployeeDao
import com.example.employeelist.dataaccess.database.entity.AddressEntity
import com.example.employeelist.dataaccess.database.entity.EmployeeEntity
import com.example.employeelist.dataaccess.database.entity.EmployeeWithAddress
import com.example.employeelist.entity.Address
import com.example.employeelist.entity.Employee
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val dao: EmployeeDao) {

    fun getAllEmployees(): Flowable<List<Employee>> =
        dao.getAllEmployees().map(::mapEmployeeWithAddress)

    fun saveEmployee(employee: Employee): Completable {
        val employeeWithAddress = EmployeeWithAddress(
            EmployeeEntity(
                employeeId = employee.employeeId,
                firstName = employee.firstName,
                lastName = employee.lastName,
                age = employee.age,
                gender = employee.gender
            ),
            addresses = employee.addresses.map {
                AddressEntity(
                    addressId = it.addressId,
                    addressWithEmployeeId = employee.employeeId,
                    street = it.street,
                    city = it.city,
                    zipCode = it.zipCode,
                    country = it.country
                )
            }
        )

        return Completable.fromAction {
            dao.insertEmployeeWithAddress(employeeWithAddress)
        }.subscribeOn(Schedulers.io())
    }

    fun removeEmployee(employee: Employee): Completable =
        Completable.fromAction {
            val employeeEntity = dao.getEmployeeById(employeeId = employee.employeeId)
            employeeEntity?.let {
                dao.delete(it.employee)
                it.addresses.forEach { address ->
                    dao.delete(address)
                }
            }
        }


    private fun mapEmployeeWithAddress(list: List<EmployeeWithAddress>) =
        list.map { it ->
            Employee(
                employeeId = it.employee.employeeId,
                firstName = it.employee.firstName,
                lastName = it.employee.lastName,
                age = it.employee.age,
                gender = it.employee.gender,
                addresses = it.addresses.map { address ->
                    Address(
                        addressId = address.addressId,
                        street = address.street,
                        city = address.city,
                        zipCode = address.zipCode,
                        country = address.country
                    )
                }
            )
        }
}