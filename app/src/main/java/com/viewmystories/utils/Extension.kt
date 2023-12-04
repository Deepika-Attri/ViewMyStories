package com.viewmystories.utils

import android.content.Intent
import android.os.Build
import java.io.Serializable

/**
 * Used to get serializable data from intent irrespective to Android version
 */
@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Intent.getSerializableList(key: String): ArrayList<T>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this.getSerializableExtra(key) as? ArrayList<T>
    } else {
        val serializableExtra = this.getSerializableExtra(key)
        if (serializableExtra is ArrayList<*>
            && serializableExtra.isNotEmpty()
            && serializableExtra[0] is T
        ) {
            @Suppress("UNCHECKED_CAST")
            return serializableExtra as ArrayList<T>
        }
        null
    }
}

