package ass.cafeburp.dine.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ass.cafeburp.dine.data.local.modals.CartItem
import ass.cafeburp.dine.databinding.ItemCategoryBinding

class CartAdapter : ListAdapter<CartItem, CartAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding: ItemCategoryBinding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.apply {

            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
            oldItem == newItem
    }


}