package com.kstd.domain.lecture.dto

data class LectureApplicantDto(
    val lectureId: Long,
    val memberId: String,
) {
    fun isNotValidMemberId(): Boolean {
        return this.memberId.length != 5
    }
}
