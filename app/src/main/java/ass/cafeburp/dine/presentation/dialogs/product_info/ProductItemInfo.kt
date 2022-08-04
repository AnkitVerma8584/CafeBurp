package ass.cafeburp.dine.presentation.dialogs.product_info

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ass.cafeburp.dine.R
import ass.cafeburp.dine.databinding.DialogProductInfoBinding
import ass.cafeburp.dine.domain.modals.Product
import ass.cafeburp.dine.util.inCurrency
import ass.cafeburp.dine.util.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductItemInfo(
    private val product: Product? = null,
    private inline val onDialogClosed: ((Unit) -> Unit)?
) : BottomSheetDialogFragment() {

    constructor() : this(null, null)

    private var _binding: DialogProductInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductItemInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogProductInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product?.let {
            binding.apply {
                viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    fun change() {
                        addToCart.apply {
                            isEnabled = false
                            text = resources.getString(R.string.in_cart)
                        }
                    }
                    if (viewModel.checkInCart(it.id)) {
                        change()
                    } else {
                        addToCart.setOnClickListener { _ ->
                            viewModel.addItem(it)
                            change()
                        }
                    }
                }
                productName.text = it.name
                productPrice.text = it.price.inCurrency()
                productCategory.text = it.category
                productDescription.text = it.description
                productImage.load(it.image)
                close.setOnClickListener { dismiss() }
            }
        } ?: dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDialogClosed?.invoke(Unit)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onDialogClosed?.invoke(Unit)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}