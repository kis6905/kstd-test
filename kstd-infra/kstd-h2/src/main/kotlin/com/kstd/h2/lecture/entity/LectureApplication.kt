package com.kstd.h2.lecture.entity

import jakarta.persistence.*

@Entity
@Table(name = "lecture_application")
class LectureApplication(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LectureApplicationId")
    var lectureApplicationId: Long? = null,

    @Column(name = "lectureId")
    var lectureId: Long? = null,

    @Column(name = "memberId")
    var memberId: String? = null,
)
