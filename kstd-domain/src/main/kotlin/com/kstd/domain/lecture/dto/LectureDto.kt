package com.kstd.domain.lecture.dto

import java.time.LocalDateTime

data class LectureDto(
    val lectureId: Long,
    val lecturer: String,
    val numberOfApplication: String,
    val lectureDate: LocalDateTime,
    val lectureContent: String,
    val lectureHall: String,
)
