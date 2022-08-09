package ass.cafeburp.dine.presentation.fragments.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ass.cafeburp.dine.R
import ass.cafeburp.dine.databinding.FragmentCartBinding
import ass.cafeburp.dine.presentation.adapters.CartAdapter
import ass.cafeburp.dine.presentation.dialogs.order.PlaceOrderDialog
import ass.cafeburp.dine.util.collectFromFlow
import ass.cafeburp.dine.util.inCurrency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private lateinit var textView: TextView

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
            cartRV.setHasFixedSize(true)
            cartRV.adapter = adapter

            viewModel.cartItems.observe(viewLifecycleOwner) {
                noResult.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                adapter.submitList(it)
                viewModel.setPrice(it)
                info.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            }

            totalPrice.setFactory {
                textView = TextView(requireContext())
                textView.textSize = 18F
                textView.typeface = ResourcesCompat.getFont(
                    requireContext(),
                    R.font.montserrat_bold
                )
                textView
            }
            collectFromFlow(viewModel.price) {
                totalPrice.setText(it.inCurrency())
            }

            proceed.setOnClickListener {
                val dialog = PlaceOrderDialog { name, mobile, table ->
                    findNavController().navigate(
                        CartFragmentDirections.actionNavCartToNavPlaceOrder(name, mobile, table)
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