package ass.cafeburp.dine.data.remote.helpers

data class RetrofitResult<T>(
    val success: Boolean,
    val message: String = "",
    val data: T?
)