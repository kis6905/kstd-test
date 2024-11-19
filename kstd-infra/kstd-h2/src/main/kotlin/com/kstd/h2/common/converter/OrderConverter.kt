package com.kstd.h2.common.converter

import com.kstd.common.condition.OrderType
import com.querydsl.core.types.Order

fun OrderType.toOrder(): Order {
    return when (this) {
        OrderType.DESC -> Order.DESC
        OrderType.ASC -> Order.ASC
    }
}
