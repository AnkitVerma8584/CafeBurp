package ass.cafeburp.dine.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ass.cafeburp.dine.databinding.FragmentHomeBinding
import ass.cafeburp.dine.domain.modals.product.Product
import ass.cafeburp.dine.domain.util.Resource
import ass.cafeburp.dine.presentation.adapters.CategoryAdapter
import ass.cafeburp.dine.presentation.adapters.ProductAdapter
import ass.cafeburp.dine.presentation.dialogs.product_info.ProductItemInfo
import ass.cafeburp.dine.presentation.dialogs.product_info.interfaces.ProductInterfaces
import ass.cafeburp.dine.util.collectFromFlow
import ass.cafeburp.dine.util.stop
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            selectedCategory.text = viewModel.categoryName.asString(requireContext())

            val adapter = CategoryAdapter {
                viewModel.setCategory(it)
                selectedCategory.text = it.category_name
            }
            categoryRV.adapter = adapter

            val productAdapter = ProductAdapter(viewModel)
            productAdapter.productInterfaces = object : ProductInterfaces {
                override fun onProductClicked(product: Product, position: Int) {
                    val dialogProductInfoBinding = ProductItemInfo(product) {
                        productAdapter.notifyItemChanged(position)
                    }
                    dialogProductInfoBinding.show(childFragmentManager, null)
                }

                override fun onAddToCart(product: Product) {
                    viewModel.addItem(product)
                }
            }

            foodRV.adapter = productAdapter

            foodRV.addOnScrollListener(this@HomeFragment.scrollListener)

            viewModel.categories.observe(viewLifecycleOwner) { res ->
                when (res) {
                    is Resource.Error -> {
                        res.message?.let {
                            Snackbar.make(
                                root,
                                it.asString(requireContext()),
                                Snackbar.LENGTH_SHORT
                            ).setAnchorView(anchorView).show()
                        }
                    }
                    is Resource.Success -> {
                        shimmerCategory.stop()
                        adapter.submitList(res.data)
                    }
                }
            }

            viewModel.products.observe(viewLifecycleOwner) { products ->
                shimmerProduct.stop()
                noResult.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
                productAdapter.submitList(products)
            }

            collectFromFlow(viewModel.productsError) {
                Snackbar.make(root, it.asString(requireContext()), Snackbar.LENGTH_LONG)
                    .setAnchorView(anchorView).show()
            }

            collectFromFlow(viewModel.isLoading) {
                progress.visibility = if (it) View.VISIBLE else View.INVISIBLE
            }

            collectFromFlow(viewModel.isLastPage) {
                foodRV.setPadding(0, 0, 0, if (it) 0 else 100)
            }
        }
    }

    private var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage =
                !viewModel.isLoading.value && !viewModel.isLastPage.value

            val isAtLastItem = firstVisibleItemPosition + visibleItemCount == totalItemCount

            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isScrolling
            if (shouldPaginate) {
                viewModel.isLoading.value = true
                isScrolling = false
                viewModel.getProducts()
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}