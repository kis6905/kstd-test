package com.kstd.domain.lecture.port.output

import com.kstd.domain.lecture.dto.LectureDto

interface LecturePersistencePort {
    fun findLectureList(): List<LectureDto>
}