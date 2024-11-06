package com.infolaft.shared

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    override fun isAndroid(): Boolean {
        return false
    }
}

actual fun getPlatform(): Platform = IOSPlatform()