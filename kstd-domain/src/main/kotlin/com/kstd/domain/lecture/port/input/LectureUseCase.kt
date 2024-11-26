package com.kstd.domain.lecture.port.input

import com.kstd.domain.lecture.dto.LectureApplicantDto
import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.dto.LectureFormDto

interface LectureUseCase {
    fun findAllLectureList(): List<LectureDto>
    fun findApplicableLectureList(): List<LectureDto>
    fun findAppliedLectureList(memberId: String): List<LectureDto>
    fun saveLecture(form: LectureFormDto): LectureDto
    fun findPopularityLecture(): LectureDto

    fun findApplicants(lectureId: Long): List<String>
    fun applyLecture(lectureApplicantDto: LectureApplicantDto)
    fun cancelLecture(lectureApplicantDto: LectureApplicantDto)
}