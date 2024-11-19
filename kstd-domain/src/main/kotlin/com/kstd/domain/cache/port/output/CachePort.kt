package com.kstd.domain.cache.port.output

interface CachePort {
    fun lock(key: String): Boolean
    fun unlock(key: String): Boolean
}