package ass.cafeburp.dine.data.local.mapper

import ass.cafeburp.dine.data.local.modals.CartItem
import ass.cafeburp.dine.domain.modals.Product

fun Product.productToCartItem(): CartItem = CartItem(id, name, category, 1, price, discount, image)