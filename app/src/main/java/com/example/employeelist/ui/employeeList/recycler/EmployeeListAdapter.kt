package com.example.employeelist.ui.employeeList.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeelist.databinding.ItemEmployeeBinding
import com.example.employeelist.entity.Employee
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class EmployeeListAdapter @Inject constructor() :
    RecyclerView.Adapter<EmployeeListViewHolder>() {

    var items: List<Employee> = emptyList()
    private val selectedItemSubject: PublishSubject<Int> = PublishSubject.create()
    private val deletedItemSubject: PublishSubject<Int> = PublishSubject.create()
    val selectedItemStream: Observable<Int> = selectedItemSubject
    val deletedItemStream: Observable<Int> = deletedItemSubject
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeBinding.inflate(inflater, parent, false)
        return EmployeeListViewHolder(
            binding = binding,
            onItemClick = selectedItemSubject,
            onDeleteClick = deletedItemSubject
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    fun setDataWithDiff(employeeList: List<Employee>) {
        this.items = employeeList
        notifyDataSetChanged()
    }
}


class EmployeeListViewHolder(
    private val binding: ItemEmployeeBinding,
    private val onItemClick: PublishSubject<Int>,
    private val onDeleteClick: PublishSubject<Int>
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: Employee

    init {
        binding.root.setOnClickListener {
            onItemClick.onNext(adapterPosition)
        }
        binding.btnRemove.setOnClickListener {
            onDeleteClick.onNext(adapterPosition)
        }
    }

    fun bind(item: Employee) {
        this.item = item
        binding.employee = item
    }

}