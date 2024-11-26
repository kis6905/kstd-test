package com.kstd.h2.config

import com.kstd.h2.KstdH2
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@EnableJpaAuditing
@EnableJpaRepositories("com.kstd.h2.*")
@EntityScan("com.kstd.h2.*")
@ComponentScan(basePackageClasses = [KstdH2::class])
class H2Config(
    private val entityManager: EntityManager
) {
    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}
