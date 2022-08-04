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
import ass.cafeburp.dine.util.inCurrency
import ass.cafeburp.dine.util.load
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
        holder.bind(position)
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val item: Product = getItem(position)
            binding.apply {
                productCategory.text = item.category
                productName.text = item.name
                productPrice.text = item.price.inCurrency()
                productImage.load(item.image)
                fun change() {
                    addToCart.apply {
                        isEnabled = false
                        text = resources.getString(R.string.in_cart)
                    }
                }
                CoroutineScope(Main).launch {
                    if (viewModel.checkInCart(item.id)) {
                        change()
                    } else {
                        addToCart.apply {
                            isEnabled = true
                            text = resources.getString(R.string.add_cart)
                        }
                    }
                }
                addToCart.setOnClickListener {
                    productInterfaces.onAddToCart(item)
                    change()
                }
                root.setOnClickListener {
                    productInterfaces.onProductClicked(item, position)
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