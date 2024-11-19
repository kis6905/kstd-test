package com.kstd.domain.lecture.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.kstd.common.helper.DateTimeConstants
import java.time.LocalDateTime

data class LectureFormDto(
    val lecturer: String,
    val applicantLimit: Long,
    @JsonFormat(pattern = DateTimeConstants.ISO_YYYY_MM_DD_HH_MM_SS)
    val lectureDate: LocalDateTime,
    val lectureContent: String,
    val lectureHall: String,
)
