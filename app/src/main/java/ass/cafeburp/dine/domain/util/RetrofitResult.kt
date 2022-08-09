package ass.cafeburp.dine.domain.util

data class RetrofitResult<T>(
    val success: Boolean,
    val message: String = "",
    val data: T?
)