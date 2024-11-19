package com.kstd.domain.lecture.service

import com.appmattus.kotlinfixture.kotlinFixture
import com.kstd.common.exception.KstdException
import com.kstd.domain.cache.port.output.CachePort
import com.kstd.domain.lecture.dto.LectureApplicantCountDto
import com.kstd.domain.lecture.dto.LectureApplicantDto
import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.port.output.LecturePersistencePort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class LectureServiceTest: BehaviorSpec({
    isolationMode = IsolationMode.InstancePerTest

    val fixture = kotlinFixture()

    val lecturePersistencePort = mockk<LecturePersistencePort>()
    val cachePort = mockk<CachePort>()

    val sut = LectureService(
        lecturePersistencePort = lecturePersistencePort,
        cachePort = cachePort,
    )

    Given("강의 신청 내역 - findAppliedLectureList()") {
        val lectureId = 1L
        val memberId = "10001"

        When("성공") {
            val applicantList = listOf(
                LectureApplicantDto(lectureId = lectureId, memberId = memberId)
            )
            val lectureList = listOf(
                fixture<LectureDto> {
                    property(LectureDto::lectureId) { lectureId }
                }
            )
            every { lecturePersistencePort.findApplicantsByMember(memberId) } answers { applicantList }
            every { lecturePersistencePort.findLectureList(any()) } answers { lectureList }

            val result = sut.findAppliedLectureList(memberId)

            Then("성공") {
                verify(exactly = 1) { lecturePersistencePort.findApplicantsByMember(memberId) }
                verify(exactly = 1) { lecturePersistencePort.findLectureList(any()) }
                result.size shouldBe 1
            }
        }

        When("실패 - 신청한 강의가 없을 때") {
            every { lecturePersistencePort.findApplicantsByMember(memberId) } answers { emptyList() }

            val result = sut.findAppliedLectureList(memberId)

            Then("return emptyList()") {
                verify(exactly = 1) { lecturePersistencePort.findApplicantsByMember(memberId) }
                verify(exactly = 0) { lecturePersistencePort.findLectureList(any()) }
                result.size shouldBe 0
            }
        }
    }

    Given("실시간 인기 강의 - findPopularityLecture()") {
        val lectureList = listOf(
            fixture<LectureDto> {
                property(LectureDto::lectureId) { 1L }
            },
            fixture<LectureDto> {
                property(LectureDto::lectureId) { 2L }
            }
        )

        When("성공") {
            val applicantCountList = listOf(
                LectureApplicantCountDto(lectureId = 1L, applicantCount = 10L),
                LectureApplicantCountDto(lectureId = 2L, applicantCount = 20L),
            )

            every { lecturePersistencePort.findLectureList(any()) } answers { lectureList }
            every { lecturePersistencePort.findApplicantCountList(any()) } answers { applicantCountList }

            val result = sut.findPopularityLecture()

            Then("성공") {
                verify(exactly = 1) { lecturePersistencePort.findLectureList(any()) }
                verify(exactly = 1) { lecturePersistencePort.findApplicantCountList(any()) }
                result.lectureId shouldBe 2L
            }
        }

        When("실패 - 신청 가능한 강의가 없을 때") {
            every { lecturePersistencePort.findLectureList(any()) } answers { emptyList() }

            Then("Exception 발생") {
                shouldThrow<KstdException> {
                    sut.findPopularityLecture()
                }.apply {
                    message shouldBe "현재 신청 가능한 강의가 없습니다."
                }

                verify(exactly = 1) { lecturePersistencePort.findLectureList(any()) }
                verify(exactly = 0) { lecturePersistencePort.findApplicantCountList(any()) }
            }
        }

        When("실패 - 신청자가 아무도 없을 때") {
            every { lecturePersistencePort.findLectureList(any()) } answers { lectureList }
            every { lecturePersistencePort.findApplicantCountList(any()) } answers { emptyList() }

            Then("Exception 발생") {
                shouldThrow<KstdException> {
                    sut.findPopularityLecture()
                }.apply {
                    message shouldBe "현재 모든 강의에 신청자가 없습니다."
                }

                verify(exactly = 1) { lecturePersistencePort.findLectureList(any()) }
                verify(exactly = 1) { lecturePersistencePort.findApplicantCountList(any()) }
            }
        }
    }

    Given("강의 신청 - applyLecture()") {
        val applicant = LectureApplicantDto(
            lectureId = 1L,
            memberId = "10001",
        )

        When("성공") {
            val applicantList = listOf(
                LectureApplicantDto(lectureId = 1L, memberId = "10002"),
                LectureApplicantDto(lectureId = 1L, memberId = "10003"),
            )
            val lecture = fixture<LectureDto> {
                property(LectureDto::lectureId) { 1L }
                property(LectureDto::applicantLimit) { 10L }
            }

            every { cachePort.lock(any()) } answers { true }
            every { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) } answers { applicantList }
            every { lecturePersistencePort.findLecture(applicant.lectureId) } answers { lecture }
            every { lecturePersistencePort.saveApplicant(applicant) } answers { applicant }
            every { cachePort.unlock(any()) } answers { true }

            sut.applyLecture(applicant)

            Then("성공") {
                verify(exactly = 1) { cachePort.lock(any()) }
                verify(exactly = 1) { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) }
                verify(exactly = 1) { lecturePersistencePort.findLecture(applicant.lectureId) }
                verify(exactly = 1) { lecturePersistencePort.saveApplicant(applicant) }
                verify(exactly = 1) { cachePort.unlock(any()) }
            }
        }

        When("실패 - Lock 획득 실패") {
            every { cachePort.lock(any()) } answers { false }
            every { cachePort.unlock(any()) } answers { true }

            Then("Exception 발생") {
                shouldThrow<KstdException> {
                    sut.applyLecture(applicant)
                }.apply {
                    message shouldBe "Lock 획득에 실패했습니다. 다시 시도해주세요."
                }

                verify(exactly = 1) { cachePort.lock(any()) }
                verify(exactly = 0) { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) }
                verify(exactly = 0) { lecturePersistencePort.findLecture(applicant.lectureId) }
                verify(exactly = 0) { lecturePersistencePort.saveApplicant(applicant) }
                verify(exactly = 1) { cachePort.unlock(any()) }
            }
        }

        When("실패 - 이미 신청한 강의일 때") {
            val applicantList = listOf(
                LectureApplicantDto(lectureId = 1L, memberId = "10001"),
                LectureApplicantDto(lectureId = 1L, memberId = "10002"),
                LectureApplicantDto(lectureId = 1L, memberId = "10003"),
            )

            every { cachePort.lock(any()) } answers { true }
            every { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) } answers { applicantList }
            every { cachePort.unlock(any()) } answers { true }

            Then("Exception 발생") {
                shouldThrow<KstdException> {
                    sut.applyLecture(applicant)
                }.apply {
                    message shouldBe "이미 신청한 강의입니다."
                }

                verify(exactly = 1) { cachePort.lock(any()) }
                verify(exactly = 1) { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) }
                verify(exactly = 0) { lecturePersistencePort.findLecture(applicant.lectureId) }
                verify(exactly = 0) { lecturePersistencePort.saveApplicant(applicant) }
                verify(exactly = 1) { cachePort.unlock(any()) }
            }
        }

        When("실패 - 강의가 없을때(실제론 발생하지 않을 케이스)") {
            val applicantList = listOf(
                LectureApplicantDto(lectureId = 1L, memberId = "10002"),
                LectureApplicantDto(lectureId = 1L, memberId = "10003"),
            )

            every { cachePort.lock(any()) } answers { true }
            every { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) } answers { applicantList }
            every { lecturePersistencePort.findLecture(applicant.lectureId) } answers { null }
            every { cachePort.unlock(any()) } answers { true }

            Then("Exception 발생") {
                shouldThrow<KstdException> {
                    sut.applyLecture(applicant)
                }.apply {
                    message shouldBe "강의를 찾을수 없습니다."
                }

                verify(exactly = 1) { cachePort.lock(any()) }
                verify(exactly = 1) { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) }
                verify(exactly = 1) { lecturePersistencePort.findLecture(applicant.lectureId) }
                verify(exactly = 0) { lecturePersistencePort.saveApplicant(applicant) }
                verify(exactly = 1) { cachePort.unlock(any()) }
            }
        }

        When("실패 - 정원이 초과됐을 때") {
            val applicantList = listOf(
                LectureApplicantDto(lectureId = 1L, memberId = "10002"),
                LectureApplicantDto(lectureId = 1L, memberId = "10003"),
            )
            val lecture = fixture<LectureDto> {
                property(LectureDto::lectureId) { 1L }
                property(LectureDto::applicantLimit) { 2L }
            }

            every { cachePort.lock(any()) } answers { true }
            every { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) } answers { applicantList }
            every { lecturePersistencePort.findLecture(applicant.lectureId) } answers { lecture }
            every { cachePort.unlock(any()) } answers { true }

            Then("Exception 발생") {
                shouldThrow<KstdException> {
                    sut.applyLecture(applicant)
                }.apply {
                    message shouldBe "정원 초과 되었습니다."
                }

                verify(exactly = 1) { cachePort.lock(any()) }
                verify(exactly = 1) { lecturePersistencePort.findApplicantsByLecture(applicant.lectureId) }
                verify(exactly = 1) { lecturePersistencePort.findLecture(applicant.lectureId) }
                verify(exactly = 0) { lecturePersistencePort.saveApplicant(applicant) }
                verify(exactly = 1) { cachePort.unlock(any()) }
            }
        }
    }
})
