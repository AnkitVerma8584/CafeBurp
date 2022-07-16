package ass.cafeburp.dine.presentation.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ass.cafeburp.dine.R
import ass.cafeburp.dine.databinding.FragmentHomeBinding
import ass.cafeburp.dine.presentation.adapters.CategoryAdapter
import ass.cafeburp.dine.util.Resource
import ass.cafeburp.dine.util.collectFromFlow
import ass.cafeburp.dine.util.states.UIState
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
        collectFromFlow(viewModel.uiState) {
            when (it) {
                UIState.Idle -> {

                }
                UIState.Loading -> {

                }
            }
        }
        val adapter = CategoryAdapter()
        binding.categoryRV.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Error -> {
                    res.message?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                    }
                }
                is Resource.Success -> {
                    adapter.submitList(res.data)
                }
            }
        }

        binding.fabCart.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}