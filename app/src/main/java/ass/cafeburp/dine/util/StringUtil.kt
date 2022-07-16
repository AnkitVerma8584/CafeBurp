package com.dtech.schoolapp.util

import android.content.Context
import androidx.annotation.StringRes

sealed class StringUtil {
    data class DynamicText(val value: String) : StringUtil()
    class StringResource(
        @StringRes val id: Int,
        vararg val args: Any
    ) : StringUtil()

    fun asString(context: Context): String =
        when (this@StringUtil) {
            is DynamicText -> value
            is StringResource -> context.getString(id, *args)
        }
}
