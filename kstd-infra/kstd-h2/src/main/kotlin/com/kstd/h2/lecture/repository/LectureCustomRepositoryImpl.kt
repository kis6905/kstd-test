package com.kstd.h2.lecture.repository

import com.kstd.common.helper.isNotNullOrNotEmpty
import com.kstd.domain.lecture.dto.LectureCondition
import com.kstd.domain.lecture.dto.LectureOrderColumn
import com.kstd.h2.common.converter.toOrder
import com.kstd.h2.lecture.entity.Lecture
import com.kstd.h2.lecture.entity.QLecture
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class LectureCustomRepositoryImpl(
    private val query: JPAQueryFactory,
): LectureCustomRepository {

    override fun findList(condition: LectureCondition): List<Lecture> {
        val lecture = QLecture.lecture
        return query.selectFrom(lecture)
            .where(buildWhereClause(lecture, condition))
            .orderBy(*buildOrderClause(lecture, condition).toTypedArray())
            .fetch()
    }

    fun buildWhereClause(lecture: QLecture, condition: LectureCondition): BooleanBuilder {
        val builder = BooleanBuilder()
        if (condition.lectureIds.isNotNullOrNotEmpty()) {
            builder.and(lecture.lectureId.`in`(condition.lectureIds))
        }
        condition.lectureTimeAfter?.let {
            builder.and(lecture.lectureDate.gt(condition.lectureTimeAfter))
        }
        condition.lectureTimeBefore?.let {
            builder.and(lecture.lectureDate.lt(condition.lectureTimeBefore))
        }
        return builder
    }

    fun buildOrderClause(lecture: QLecture, condition: LectureCondition): List<OrderSpecifier<*>> {
        return condition.orderConditions.map {
            when (it.column) {
                LectureOrderColumn.LECTURE_DATE -> {
                    OrderSpecifier(it.orderType.toOrder(), lecture.lectureDate)
                }
            }
        }
    }
}