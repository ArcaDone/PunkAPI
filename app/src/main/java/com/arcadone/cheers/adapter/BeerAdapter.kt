package com.arcadone.cheers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arcadone.cheers.databinding.RecyclerviewItemBinding
import com.arcadone.cheers.model.BeerItem

class BeerAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<BeerItem, BeerAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClickListener.onClick(item) }
        holder.bind(item)
    }

    class ItemViewHolder(private val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BeerItem) {
            binding.beerItem = item
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (item: BeerItem) -> Unit) {
        fun onClick(item: BeerItem) = clickListener(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<BeerItem>() {
        override fun areItemsTheSame(oldItem: BeerItem, newItem: BeerItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BeerItem, newItem: BeerItem): Boolean {
            return oldItem == newItem
        }
    }
}
