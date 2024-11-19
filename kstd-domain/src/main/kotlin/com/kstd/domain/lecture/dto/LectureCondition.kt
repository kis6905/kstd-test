package com.kstd.domain.lecture.dto

import com.kstd.common.condition.OrderType
import java.time.LocalDateTime

data class LectureCondition(
    val lectureIds: List<Long>? = null,
    val lectureTimeBefore: LocalDateTime? = null,
    val lectureTimeAfter: LocalDateTime? = null,
    val orderConditions: List<LectureOrderCondition> = listOf(LectureOrderCondition.default)
)

data class LectureOrderCondition(
    val column: LectureOrderColumn,
    val orderType: OrderType,
) {
    companion object {
        val default = LectureOrderCondition(
            column = LectureOrderColumn.LECTURE_DATE,
            orderType = OrderType.DESC,
        )
    }
}

enum class LectureOrderColumn(
    val columnName: String,
) {
    LECTURE_DATE("lectureDate"),
}
