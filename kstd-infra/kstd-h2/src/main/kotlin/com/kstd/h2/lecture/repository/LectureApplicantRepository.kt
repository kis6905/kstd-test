package com.kstd.h2.lecture.repository

import com.kstd.h2.lecture.entity.LectureApplicant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureApplicantRepository: JpaRepository<LectureApplicant, Long>, LectureApplicantCustomRepository {
    fun findByLectureId(lectureId: Long): List<LectureApplicant>
    fun findByMemberId(memberId: String): List<LectureApplicant>
    fun findByLectureIdAndMemberId(lectureId: Long, memberId: String): LectureApplicant?
}
