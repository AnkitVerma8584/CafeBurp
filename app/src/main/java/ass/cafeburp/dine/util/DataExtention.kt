package ass.cafeburp.dine.util

import android.util.Log
import java.math.RoundingMode
import java.text.DecimalFormat

fun Any?.printLog(tag: String = "TAG", error: Exception? = null) {
    Log.e(tag, this.toString(), error)
}

fun Double.inCurrency(): String {
    val df = DecimalFormat("0.0")
    df.roundingMode = RoundingMode.HALF_UP
    return String.format("₹ %s", df.format(this))
}
