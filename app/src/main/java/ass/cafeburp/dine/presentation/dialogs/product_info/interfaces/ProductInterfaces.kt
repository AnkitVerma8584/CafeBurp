package ass.cafeburp.dine.presentation.dialogs.product_info.interfaces

import ass.cafeburp.dine.domain.modals.Product

interface ProductInterfaces {

    fun onProductClicked(product: Product, position: Int)

    fun onAddToCart(product: Product)
}