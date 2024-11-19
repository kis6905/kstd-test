package com.kstd.cache.config

import com.kstd.cache.KstdCache
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [KstdCache::class])
class CacheConfig {
}