package ass.cafeburp.dine.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import ass.cafeburp.dine.data.remote.helpers.Resource
import ass.cafeburp.dine.databinding.FragmentHomeBinding
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.presentation.MainViewModel
import ass.cafeburp.dine.presentation.adapters.CategoryAdapter
import ass.cafeburp.dine.presentation.adapters.ProductAdapter
import ass.cafeburp.dine.presentation.dialogs.product_info.ProductItemInfo
import ass.cafeburp.dine.presentation.dialogs.product_info.interfaces.ProductInterfaces
import ass.cafeburp.dine.util.stop
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
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
        val adapter = CategoryAdapter {
            binding.selectedCategory.text = it.categoryName
            viewModel.getCategoryProducts(it.id)
        }

        val productAdapter = ProductAdapter(mainViewModel)

        productAdapter.productInterfaces = object : ProductInterfaces {
            override fun onProductClicked(product: Product, position: Int) {
                val dialogProductInfoBinding = ProductItemInfo(product) {
                    productAdapter.notifyItemChanged(position)
                }
                dialogProductInfoBinding.show(childFragmentManager, null)
            }

            override fun onAddToCart(product: Product) {
                mainViewModel.addItem(product)
            }
        }

        binding.apply {
            categoryRV.adapter = adapter
            foodRV.adapter = productAdapter
            viewModel.categories.observe(viewLifecycleOwner) { res ->
                when (res) {
                    is Resource.Error -> {
                        res.message?.let {
                            Snackbar.make(
                                root, it.asString(requireContext()), Snackbar.LENGTH_SHORT
                            ).show()
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
                when (products) {
                    is Resource.Error -> {
                        noResult.visibility = View.VISIBLE
                        products.message?.let {
                            Snackbar.make(
                                root, it.asString(requireContext()), Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    is Resource.Success -> {
                        noResult.visibility = View.GONE
                        productAdapter.submitList(products.data)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}