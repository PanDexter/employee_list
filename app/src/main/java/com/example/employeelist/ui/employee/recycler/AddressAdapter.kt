package com.example.employeelist.ui.employee.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.employeelist.databinding.ItemAddressBinding
import com.example.employeelist.entity.Address
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AddressAdapter @Inject constructor() : RecyclerView.Adapter<AddressViewHolder>() {

    var items: List<Address> = emptyList()
    private val selectedItemSubject: PublishSubject<Int> = PublishSubject.create()
    val selectedItemStream: Observable<Int> = selectedItemSubject
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddressBinding.inflate(inflater, parent, false)
        return AddressViewHolder(
            binding = binding,
            onItemClick = selectedItemSubject
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    fun setDataWithDiff(addressList: List<Address>) {
        this.items = addressList
        notifyDataSetChanged()
    }

    fun addDataWithDiff(address: Address) {
        this.items += address
        notifyDataSetChanged()
    }


}


class AddressViewHolder(
    private val binding: ItemAddressBinding,
    private val onItemClick: PublishSubject<Int>
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var item: Address

    init {
        itemView.setOnClickListener {
            onItemClick.onNext(adapterPosition)
        }
    }

    fun bind(item: Address) {
        this.item = item
        binding.address = item
    }
}