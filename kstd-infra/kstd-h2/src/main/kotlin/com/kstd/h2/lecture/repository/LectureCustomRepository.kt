package com.kstd.h2.lecture.repository

import com.kstd.domain.lecture.dto.LectureCondition
import com.kstd.h2.lecture.entity.Lecture

interface LectureCustomRepository {
    fun findList(condition: LectureCondition): List<Lecture>
}