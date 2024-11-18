package com.kstd.h2.lecture.converter

import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.h2.lecture.entity.Lecture

fun List<Lecture>?.toLectureDto(): List<LectureDto> {
    return this?.let {
        it.map { entity -> entity.toLectureDto() }
    } ?: emptyList()
}

fun Lecture.toLectureDto(): LectureDto {
    return LectureDto(
        lectureId = this.lectureId!!,
        lecturer = this.lecturer!!,
        numberOfApplication = this.numberOfApplication!!,
        lectureDate = this.lectureDate!!,
        lectureContent = this.lectureContent!!,
        lectureHall = this.lectureHall!!,
    )
}
