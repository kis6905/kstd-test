package com.kstd.h2.lecture.projection

import com.querydsl.core.annotations.QueryProjection

class LectureApplicantCount @QueryProjection constructor(
    val lectureId: Long,
    val applicantCount: Long,
)
