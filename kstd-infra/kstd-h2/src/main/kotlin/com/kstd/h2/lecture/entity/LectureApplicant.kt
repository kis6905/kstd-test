package com.kstd.h2.lecture.entity

import jakarta.persistence.*

@Entity
@Table(name = "lecture_applicant")
class LectureApplicant(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lectureApplicantId")
    var lectureApplicantId: Long? = null,

    @Column(name = "lectureId")
    var lectureId: Long? = null,

    @Column(name = "memberId")
    var memberId: String? = null,
)
