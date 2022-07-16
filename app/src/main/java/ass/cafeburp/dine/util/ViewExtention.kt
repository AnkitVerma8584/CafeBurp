package ass.cafeburp.dine.util

import android.graphics.Color
import android.view.View
import android.widget.*
import ass.cafeburp.dine.R
import coil.load
import coil.transform.RoundedCornersTransformation
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

fun ShimmerFrameLayout.stop() {
    stopShimmer()
    visibility = View.GONE
}

fun ShimmerFrameLayout.start() {
    startShimmer()
    visibility = View.VISIBLE
}

inline fun <T> Spinner.getSelectedItem(crossinline item: (item: T) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            item.invoke(p0!!.getItemAtPosition(p2) as T)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}

fun TextInputEditText.getString(): String = this.text.toString().trim()

fun EditText.getString(): String = this.text.toString().trim()

fun TextView.getString(): String = this.text.toString().trim()

fun View.showMessage(message: String) {
    val snack: Snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snack.setAction("Ok") {
        snack.dismiss()
    }
    snack.setBackgroundTint(Color.GREEN)
    snack.setTextColor(Color.BLACK)
    snack.setActionTextColor(Color.BLACK)
    snack.show()
}

fun View.showError(error: String) {
    val snack: Snackbar = Snackbar.make(this, error, Snackbar.LENGTH_LONG)
    snack.setAction("Dismiss") { snack.dismiss() }
    snack.setTextColor(Color.WHITE)
    snack.setActionTextColor(Color.WHITE)
    snack.setBackgroundTint(Color.RED)
    snack.show()
}

fun ImageView.setImage(
    image: String,
    radius: Float = 0f,
    placeholder: Int = R.drawable.placeholder
) {
    if (image.isNotNullOrEmpty())
        this.load(image) {
            placeholder(placeholder)
            error(placeholder)
            transformations(RoundedCornersTransformation(radius))
        }
}
