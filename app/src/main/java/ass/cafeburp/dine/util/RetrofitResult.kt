package ass.cafeburp.dine.util

data class RetrofitResult<T>(
    val success: Boolean,
    val message: String = "",
    val data: T?
)