package com.kstd.h2.common.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditingEntity(
    @Column(name = "createdBy")
    var createdBy: String,

    @CreationTimestamp
    @CreatedDate
    @Column(name = "createdAt")
    var createdAt: LocalDateTime? = null,

    @Column(name = "modifiedBy")
    var modifiedBy: String? = null,

    @CreationTimestamp
    @LastModifiedDate
    @Column(name = "modifiedAt")
    var modifiedAt: LocalDateTime? = null,
) {
    companion object {
        const val SYSTEM: String = "SYSTEM"
    }
}
