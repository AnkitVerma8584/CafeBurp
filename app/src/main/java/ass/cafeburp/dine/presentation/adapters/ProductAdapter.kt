package ass.cafeburp.dine.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ass.cafeburp.dine.R
import ass.cafeburp.dine.databinding.ItemProductBinding
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.presentation.MainViewModel
import ass.cafeburp.dine.presentation.dialogs.product_info.interfaces.ProductInterfaces
import coil.load
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ProductAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallback()) {

    lateinit var productInterfaces: ProductInterfaces

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val binding: ItemProductBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            viewModel.cartItems
            binding.apply {
                productCategory.text = item.category
                productName.text = item.name
                productPrice.text = String.format("₹ %s", item.totalPrice)
                productImage.load(item.image)

                fun change() {
                    addToCart.apply {
                        isEnabled = false
                        text = resources.getString(R.string.in_cart)
                    }
                }

                CoroutineScope(Main).launch {
                    if (viewModel.checkInCart(getItem(adapterPosition).id)) {
                        change()
                    } else {
                        addToCart.setOnClickListener {
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                productInterfaces.onAddToCart(getItem(adapterPosition))
                                change()
                            }
                        }
                    }
                }
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        productInterfaces.onProductClicked(
                            getItem(adapterPosition),
                            adapterPosition
                        )
                    }
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem == newItem
    }

}