package com.kstd.common.exception

class KstdException(
    override val message: String = ""
): RuntimeException(message) {
}
