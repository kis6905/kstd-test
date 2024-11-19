package com.kstd.h2.lecture.repository

import com.kstd.h2.lecture.projection.LectureApplicantCount

interface LectureApplicantCustomRepository {
    fun findGroupByLecture(lectureIds: List<Long>): List<LectureApplicantCount>
}