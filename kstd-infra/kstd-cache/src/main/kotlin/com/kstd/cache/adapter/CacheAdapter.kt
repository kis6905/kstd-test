package com.kstd.cache.adapter

import com.kstd.cache.client.CacheClient
import com.kstd.domain.cache.port.output.CachePort
import org.springframework.stereotype.Component

@Component
class CacheAdapter(
    private val cacheClient: CacheClient,
): CachePort {
    override fun lock(key: String): Boolean {
        return cacheClient.lock(key)
    }

    override fun unlock(key: String): Boolean {
        return cacheClient.unlock(key)
    }
}
