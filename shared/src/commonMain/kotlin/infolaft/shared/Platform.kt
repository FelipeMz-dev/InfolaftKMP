package com.infolaft.shared

interface Platform {
    val name: String

    fun isAndroid(): Boolean
}

expect fun getPlatform(): Platform
