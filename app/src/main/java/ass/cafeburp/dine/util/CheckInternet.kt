package ass.cafeburp.dine.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckInternet @Inject constructor(@ApplicationContext private val context: Context) {

    fun hasNoInternetConnection(): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return true
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return true
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) ||
                    capabilities.hasTransport(TRANSPORT_CELLULAR) ||
                    capabilities.hasTransport(TRANSPORT_ETHERNET) -> false
            else -> true
        }
    }
}