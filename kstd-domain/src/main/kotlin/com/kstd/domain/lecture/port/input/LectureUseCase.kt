package com.kstd.domain.lecture.port.input

import com.kstd.domain.lecture.dto.LectureDto

interface LectureUseCase {
    fun findLectureList(): List<LectureDto>
}