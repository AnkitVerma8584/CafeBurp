package ass.cafeburp.dine.data.local.mapper

import ass.cafeburp.dine.data.local.modals.CartItem
import ass.cafeburp.dine.domain.modals.order.OrderItem
import ass.cafeburp.dine.domain.modals.product.Product

fun Product.productToCartItem(): CartItem = CartItem(id, name, category, 1, price, image)

fun CartItem.mapItem(): OrderItem = OrderItem(id, quantity, price)