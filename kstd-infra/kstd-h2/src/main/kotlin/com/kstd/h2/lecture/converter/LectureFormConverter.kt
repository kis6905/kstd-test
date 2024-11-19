package com.kstd.h2.lecture.converter

import com.kstd.domain.lecture.dto.LectureFormDto
import com.kstd.h2.lecture.entity.Lecture

fun LectureFormDto.toLecture(): Lecture {
    return Lecture(
        lecturer = this.lecturer,
        applicantLimit = this.applicantLimit,
        lectureDate = this.lectureDate,
        lectureContent = this.lectureContent,
        lectureHall = this.lectureHall,
    )
}
