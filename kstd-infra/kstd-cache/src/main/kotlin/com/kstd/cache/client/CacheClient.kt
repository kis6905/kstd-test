package com.kstd.cache.client

import org.springframework.stereotype.Component

@Component
class CacheClient(
    val cache: MutableMap<String, Any> = mutableMapOf()
) {
    // SETNX 방식(spin lock)
    fun lock(key: String, retryLimit: Int = 3, interval: Long = 500): Boolean {
        var retryCount = 0
        while (retryCount < retryLimit) {
            if (cache[key] == null) {
                cache[key] = 1
                return true
            }
            retryCount++
            Thread.sleep(interval)
        }
        return false
    }

    fun unlock(key: String): Boolean {
        cache.remove(key)
        return true
    }
}
