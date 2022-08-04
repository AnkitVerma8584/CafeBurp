package ass.cafeburp.dine.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ass.cafeburp.dine.data.local.modals.CartItem
import ass.cafeburp.dine.databinding.ItemCartBinding
import ass.cafeburp.dine.util.inCurrency
import ass.cafeburp.dine.util.load

open class CartAdapter(
    private inline val onAdd: (CartItem) -> Unit,
    private inline val onMinus: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding: ItemCartBinding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.apply {
                productName.text = item.name
                productCategory.text = item.category
                productImage.load(item.image)
                quantity.text = item.quantity.toString()
                productPrice.text = item.price.inCurrency()
                add.setOnClickListener {
                    onAdd.invoke(getItem(adapterPosition))
                }
                minus.setOnClickListener {
                    onMinus.invoke(getItem(adapterPosition))
                }
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