package ass.cafeburp.dine.presentation.dialogs.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ass.cafeburp.dine.databinding.DialogPlaceOrderBinding
import ass.cafeburp.dine.util.checkEditText
import ass.cafeburp.dine.util.checkName
import ass.cafeburp.dine.util.checkPhone
import ass.cafeburp.dine.util.getString
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceOrderDialog(
    private inline val onConfirmed: ((String, String, String) -> Unit)?
) : BottomSheetDialogFragment() {

    constructor() : this(null)

    private var _binding: DialogPlaceOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogPlaceOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            cancelButton.setOnClickListener {
                dismiss()
            }
            confirmButton.setOnClickListener {
                if (checkName(nameIl, nameEt) && checkPhone(mobileIl, mobileEt) && checkEditText(
                        tableIl,
                        tableEt
                    )
                )
                    onConfirmed?.invoke(
                        nameEt.getString(),
                        mobileEt.getString(),
                        tableEt.getString()
                    ) ?: dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}