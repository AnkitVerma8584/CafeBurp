package ass.cafeburp.dine.presentation.dialogs.product_info.interfaces

import ass.cafeburp.dine.domain.modals.product.Product

interface ProductInterfaces {

    fun onProductClicked(product: Product, position: Int)

    fun onAddToCart(product: Product)
}