package com.kstd.h2.lecture.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "lecture")
class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lectureId")
    var lectureId: Long? = null,

    @Column(name = "lecturer")
    var lecturer: String? = null,

    @Column(name = "applicantLimit")
    var applicantLimit: Long? = null,

    @Column(name = "lectureDate")
    var lectureDate: LocalDateTime? = null,

    @Column(name = "lectureContent")
    var lectureContent: String? = null,

    @Column(name = "lectureHall")
    var lectureHall: String? = null,
)
