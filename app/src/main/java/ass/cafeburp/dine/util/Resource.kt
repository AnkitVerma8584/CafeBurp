package ass.cafeburp.dine.util

sealed class Resource<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String? = null) :
        Resource<T>(data = data, message = message)
}