package com.kstd.h2.lecture.repository

import com.kstd.h2.lecture.entity.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureRepository: JpaRepository<Lecture, Long> {
}
