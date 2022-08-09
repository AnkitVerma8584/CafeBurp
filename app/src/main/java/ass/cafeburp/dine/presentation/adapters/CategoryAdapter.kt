package ass.cafeburp.dine.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ass.cafeburp.dine.databinding.ItemCategoryBinding
import ass.cafeburp.dine.domain.modals.category.Category
import ass.cafeburp.dine.util.load

class CategoryAdapter(private inline val onCategoryClicked: (Category) -> Unit) :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val binding: ItemCategoryBinding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.apply {
                categoryName.text = item.category_name
                categoryImage.load(item.image)
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION)
                        onCategoryClicked.invoke(getItem(adapterPosition))
                }
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }


}