package com.kstd.domain.lecture.port.output

import com.kstd.domain.lecture.dto.LectureApplicantDto
import com.kstd.domain.lecture.dto.LectureCondition
import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.dto.LectureFormDto

interface LecturePersistencePort {
    fun findLectureList(condition: LectureCondition): List<LectureDto>
    fun findLecture(lectureId: Long): LectureDto?
    fun saveLecture(form: LectureFormDto): LectureDto
    fun findApplicants(lectureId: Long): List<LectureApplicantDto>
    fun findApplicant(lectureId: Long, memberId: String): LectureApplicantDto?
    fun saveApplicants(lectureApplicantDto: LectureApplicantDto): LectureApplicantDto
}