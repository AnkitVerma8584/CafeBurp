package ass.cafeburp.dine.util

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ass.cafeburp.dine.R
import coil.load
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.regex.Pattern

fun <T> Fragment.collectFromFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun ShimmerFrameLayout.stop() {
    stopShimmer()
    visibility = View.GONE
}

fun TextInputEditText.getString(): String = this.text.toString().trim()


fun Fragment.checkPhone(textInputLayout: TextInputLayout, editText: TextInputEditText): Boolean {
    val pattern = Pattern.compile("[0-9]+")
    val matcher = pattern.matcher(editText.text.toString())
    if (editText.text.toString().trim().isEmpty()) {
        textInputLayout.error = resources.getString(R.string.emptyPhone)
        return false
    } else if (editText.text.toString()
            .trim().length != 10 || !matcher.matches() || editText.text.toString()[0] == '0' || editText.text.toString()
            .trim().contains("*")
    ) {
        textInputLayout.error = resources.getString(R.string.invalidPhone)
        return false
    }
    textInputLayout.error = null
    return true
}

fun Fragment.checkEditText(inputLayout: TextInputLayout, editText: TextInputEditText): Boolean {
    if (editText.text.toString().trim().isEmpty()) {
        inputLayout.parent.requestChildFocus(inputLayout, inputLayout)
        inputLayout.error = resources.getString(R.string.emptyField)
        return false
    }
    inputLayout.error = null
    return true
}

fun Fragment.checkName(textInputLayout: TextInputLayout, editText: TextInputEditText): Boolean {
    if (editText.text.toString().trim().isEmpty()) {
        textInputLayout.parent.requestChildFocus(textInputLayout, textInputLayout)
        textInputLayout.error = resources.getString(R.string.emptyName)
        return false
    }
    val pattern = Pattern.compile(".*\\d.*")
    val matcher = pattern.matcher(editText.text.toString().trim())
    if (matcher.matches()) {
        textInputLayout.error = resources.getString(R.string.invalidName)
        return false
    }
    textInputLayout.error = null
    return true
}

fun ImageView.load(url: String) {
    this.load(url) {
        placeholder(R.drawable.placeholder)
        crossfade(600)
    }
}

