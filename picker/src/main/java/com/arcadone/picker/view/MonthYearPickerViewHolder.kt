package com.arcadone.picker.view

import androidx.recyclerview.widget.RecyclerView
import com.arcadone.picker.databinding.ItemChipBinding
import com.arcadone.picker.model.Item
import com.arcadone.picker.view.adapter.MonthYearPickerAdapter

internal class MonthYearPickerViewHolder(private val binding: ItemChipBinding) :
    RecyclerView.ViewHolder(binding.root) {
    internal fun bindData(
        item: Item,
        listener: MonthYearPickerAdapter.OnChipSelectListener,
        tabPosition: Int
    ) {
        binding.chip.text = item.name
        binding.chip.isChecked = item.isSelected
        binding.chip.setOnClickListener { listener.onChipSelected(item, tabPosition) }
    }
}