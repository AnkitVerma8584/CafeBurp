package ass.cafeburp.dine.data.remote

object Api {
    const val BASE_URL: String = "https://admin.cafeburp.com/api/"
    const val GET_CATEGORIES: String = "category.php"
    const val GET_FOOD: String = "product.php"

    //https://admin.cafeburp.com/api/category.php
    //https://admin.cafeburp.com/api/product.php?cat_id=1

    const val POST_ORDER: String = "order.php"

    const val FOOD_LIMIT: Int = 10
}