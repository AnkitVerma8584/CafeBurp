package ass.cafeburp.dine.data.remote.helpers

sealed class Resource<T>(
    val data: T? = null,
    val message: StringUtil? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: StringUtil) : Resource<T>(message = message)
}