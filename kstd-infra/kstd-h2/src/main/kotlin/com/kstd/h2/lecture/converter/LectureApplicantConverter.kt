package com.kstd.h2.lecture.converter

import com.kstd.domain.lecture.dto.LectureApplicantDto
import com.kstd.h2.lecture.entity.LectureApplicant

fun List<LectureApplicant>?.toLectureApplicantDto(): List<LectureApplicantDto> {
    return this?.let {
        it.map { entity -> entity.toLectureApplicantDto() }
    } ?: emptyList()
}

fun LectureApplicant.toLectureApplicantDto(): LectureApplicantDto {
    return LectureApplicantDto(
        lectureId = this.lectureId!!,
        memberId = this.memberId!!,
    )
}

fun LectureApplicantDto.toLectureApplicant(): LectureApplicant {
    return LectureApplicant(
        lectureId = this.lectureId,
        memberId = this.memberId,
    )
}
