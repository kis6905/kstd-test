package com.kstd.common.helper

fun List<Any>?.isNotNullOrNotEmpty(): Boolean {
    return !this.isNullOrEmpty()
}