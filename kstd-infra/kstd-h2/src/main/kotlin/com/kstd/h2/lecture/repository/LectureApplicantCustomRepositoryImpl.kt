package com.kstd.h2.lecture.repository

import com.kstd.h2.lecture.entity.QLectureApplicant
import com.kstd.h2.lecture.projection.LectureApplicantCount
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory

class LectureApplicantCustomRepositoryImpl(
    private val query: JPAQueryFactory,
): LectureApplicantCustomRepository {

    override fun findGroupByLecture(lectureIds: List<Long>): List<LectureApplicantCount> {
        val lectureApplicant = QLectureApplicant.lectureApplicant
        return query.select(
                Projections.constructor(
                    LectureApplicantCount::class.java,
                    lectureApplicant.lectureId,
                    lectureApplicant.memberId.count().`as`("applicantCount"),
                )
            )
            .from(lectureApplicant)
            .groupBy(lectureApplicant.lectureId)
            .fetch()
    }
}