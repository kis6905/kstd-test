package com.kstd.common.helper

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeConstants {
    const val ISO_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss"

    val ISO_YYYY_MM_DD_HH_MM_SS_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern(ISO_YYYY_MM_DD_HH_MM_SS)
}

fun String.toLocalDateTime(formatter: DateTimeFormatter): LocalDateTime {
    return LocalDateTime.parse(this, formatter)
}
