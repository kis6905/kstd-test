package com.kstd.domain.config

import com.kstd.domain.KstdDomain
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Order(Ordered.LOWEST_PRECEDENCE)
@Configuration
@ComponentScan(basePackageClasses = [KstdDomain::class])
class KstdDomainConfig {
}
