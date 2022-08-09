package ass.cafeburp.dine.presentation.fragments.place_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ass.cafeburp.dine.R
import ass.cafeburp.dine.domain.util.Resource
import ass.cafeburp.dine.databinding.FragmentPlaceOrderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class PlaceOrderFragment : Fragment() {

    private var _binding: FragmentPlaceOrderBinding? = null
    private val binding: FragmentPlaceOrderBinding get() = _binding!!

    private val viewModel: PlaceOrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaceOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            fun loadAnimation(
                @RawRes res: Int,
                message: String? = resources.getString(R.string.internal_error)
            ) {
                lottie.apply {
                    setAnimation(res)
                    repeatCount = 0
                    playAnimation()
                }
                status.text = message
            }

            viewModel.orderState.observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error -> loadAnimation(
                        R.raw.failure,
                        it.message?.asString(requireContext())
                    )

                    is Resource.Success -> {
                        loadAnimation(R.raw.success, it.data)
                        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                            viewModel.emptyCart()
                            delay(3000)
                            findNavController().navigate(PlaceOrderFragmentDirections.actionNavPlaceOrderToNavHome())
                        }
                    }
                }
            }
        }
    }


}