package com.infolaft.shared

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override fun isAndroid(): Boolean {
        return true
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()