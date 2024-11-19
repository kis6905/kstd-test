package com.kstd.h2.lecture.converter

import com.kstd.domain.lecture.dto.LectureApplicantCountDto
import com.kstd.h2.lecture.projection.LectureApplicantCount

fun List<LectureApplicantCount>?.toLectureApplicantCountDto(): List<LectureApplicantCountDto> {
    return this?.let {
        it.map { entity -> entity.toLectureApplicantCountDto() }
    } ?: emptyList()
}

fun LectureApplicantCount.toLectureApplicantCountDto(): LectureApplicantCountDto {
    return LectureApplicantCountDto(
        lectureId = this.lectureId,
        applicantCount = this.applicantCount,
    )
}