package com.arcadone.picker.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arcadone.picker.R
import com.arcadone.picker.databinding.ItemChipBinding
import com.arcadone.picker.model.Item
import com.arcadone.picker.view.MonthYearPickerViewHolder

internal class MonthYearPickerAdapter(
    private var dataSet: List<Item>,
    private val tabPosition: Int,
    private val listener: OnChipSelectListener
) : RecyclerView.Adapter<MonthYearPickerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthYearPickerViewHolder {
        val binding = ItemChipBinding.inflate(LayoutInflater.from(parent.context))
        return MonthYearPickerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: MonthYearPickerViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bindData(item, listener, tabPosition)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_chip
    }

    internal interface OnChipSelectListener {
        fun onChipSelected(item: Item, tabPosition: Int)
    }

    internal fun setData(dataSet: List<Item>) {
        this.dataSet = dataSet
    }
}