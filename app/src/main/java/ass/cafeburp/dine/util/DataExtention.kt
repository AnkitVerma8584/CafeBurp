package ass.cafeburp.dine.util

import android.util.Log
import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.inCurrency(): String {
    val df = DecimalFormat("0.0")
    df.roundingMode = RoundingMode.HALF_UP
    return String.format("₹ %s", df.format(this))
}

fun Any?.printLog(tag: String = "TAG") {
    Log.e(tag, this.toString())
}


