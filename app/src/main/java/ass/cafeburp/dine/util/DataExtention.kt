package ass.cafeburp.dine.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.math.RoundingMode
import java.text.DecimalFormat

fun <T> AppCompatActivity.collectFromFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> Fragment.collectFromFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun <T> DialogFragment.collectFromFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun Context.makeToast(text: String?) {
    text.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
}

fun String.getBody(): RequestBody = this.toRequestBody("text/plain".toMediaTypeOrNull())

fun Any.getBody(): RequestBody = this.toString().toRequestBody("text/plain".toMediaTypeOrNull())

fun Any?.printLog(tag: String = "TAG", error: Exception? = null) {
    Log.e(tag, this.toString(), error)
}

fun Float.getDoubleFormat(): String {
    val df = DecimalFormat("0.0")
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(this)
}

fun Double.getPercentage(): String {
    val df = DecimalFormat("0.0")
    df.roundingMode = RoundingMode.HALF_UP
    return df.format(this).plus(" %")
}

fun String?.isNotNullOrEmpty(): Boolean = (this != null && this.isNotBlank())

