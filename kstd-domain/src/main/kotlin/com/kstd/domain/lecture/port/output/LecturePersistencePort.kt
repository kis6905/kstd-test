package com.kstd.domain.lecture.port.output

import com.kstd.domain.lecture.dto.*

interface LecturePersistencePort {
    fun findLectureList(condition: LectureCondition): List<LectureDto>
    fun findLecture(lectureId: Long): LectureDto?
    fun saveLecture(form: LectureFormDto): LectureDto

    fun findApplicantsByLecture(lectureId: Long): List<LectureApplicantDto>
    fun findApplicantsByMember(memberId: String): List<LectureApplicantDto>
    fun findApplicant(lectureId: Long, memberId: String): LectureApplicantDto?
    fun saveApplicant(lectureApplicantDto: LectureApplicantDto): LectureApplicantDto
    fun removeApplicant(lectureApplicantDto: LectureApplicantDto)
    fun findApplicantCountList(lectureIds: List<Long>): List<LectureApplicantCountDto>
}