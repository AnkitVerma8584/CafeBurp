package ass.cafeburp.dine.presentation.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ass.cafeburp.dine.databinding.FragmentCartBinding
import ass.cafeburp.dine.presentation.MainViewModel
import ass.cafeburp.dine.presentation.adapters.CartAdapter
import ass.cafeburp.dine.presentation.dialogs.order.PlaceOrderDialog
import ass.cafeburp.dine.util.collectFromFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CartAdapter(onAdd = { viewModel.increaseItem(it) },
            onMinus = { viewModel.decreaseItem(it) })

        binding.apply {
            cartRV.adapter = adapter
            mainViewModel.cartItems.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                viewModel.setPrice(it)
                info.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            }
            collectFromFlow(viewModel.price) {
                totalPrice.text = String.format("₹ %s", it)
            }

            proceed.setOnClickListener {
                val dialog = PlaceOrderDialog { mobile, table ->
                    findNavController().navigate(
                        CartFragmentDirections.actionNavCartToNavPlaceOrder(mobile, table)
                    )
                }
                dialog.show(childFragmentManager, null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}