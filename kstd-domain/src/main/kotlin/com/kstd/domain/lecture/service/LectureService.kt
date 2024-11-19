package com.kstd.domain.lecture.service

import com.kstd.common.exception.KstdException
import com.kstd.domain.cache.port.output.CachePort
import com.kstd.domain.lecture.dto.*
import com.kstd.domain.lecture.policy.LecturePolicy
import com.kstd.domain.lecture.port.input.LectureUseCase
import com.kstd.domain.lecture.port.output.LecturePersistencePort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
internal class LectureService(
    private val lecturePersistencePort: LecturePersistencePort,
    private val cachePort: CachePort,
): LectureUseCase {

    companion object {
        private const val LOCK_KEY = "lock:lecture:application"
    }

    override fun findAllLectureList(): List<LectureDto> {
        return lecturePersistencePort.findLectureList(LectureCondition())
    }

    override fun findApplicableLectureList(): List<LectureDto> {
        val condition = LectureCondition(
            lectureTimeBefore = LocalDateTime.now().plusDays(LecturePolicy.POSSIBLE_APPLICATION_BEFORE_DAYS),
            lectureTimeAfter = LocalDateTime.now().minusDays(LecturePolicy.POSSIBLE_APPLICATION_AFTER_DAYS),
        )
        return lecturePersistencePort.findLectureList(condition)
    }

    override fun findAppliedLectureList(memberId: String): List<LectureDto> {
        val applicantList = lecturePersistencePort.findApplicantsByMember(memberId)
        if (applicantList.isEmpty()) {
            return emptyList()
        }

        val condition = LectureCondition(
            lectureIds = applicantList.map { it.lectureId }
        )
        return lecturePersistencePort.findLectureList(condition)
    }

    override fun saveLecture(form: LectureFormDto): LectureDto {
        return lecturePersistencePort.saveLecture(form)
    }

    override fun findPopularityLecture(): LectureDto {
        val applicableLectureList = findApplicableLectureList()
        if (applicableLectureList.isEmpty()) {
            throw KstdException("현재 신청중인 강의가 없습니다.")
        }

        val lectureIds = applicableLectureList.map { it.lectureId }
        val applicantCountList = lecturePersistencePort.findApplicantCountList(lectureIds)
        val popularityLecture: LectureApplicantCountDto = applicantCountList.maxByOrNull { it.applicantCount }
            ?: throw KstdException("현재 모든 강의에 신청자가 없습니다.")

        return applicableLectureList.find { it.lectureId == popularityLecture.lectureId }
            ?: throw KstdException()
    }

    override fun findApplicants(lectureId: Long): List<String> {
        return lecturePersistencePort.findApplicantsByLecture(lectureId)
            .map { it.memberId }
    }

    override fun applyLecture(lectureApplicantDto: LectureApplicantDto) {
        if (lectureApplicantDto.isNotValidMemberId()) {
            throw KstdException("사번이 유효하지 않습니다.")
        }

        try {
            val isAcquiredLock = cachePort.lock(LOCK_KEY)
            if (!isAcquiredLock) {
                throw KstdException("Lock 획득에 실패했습니다. 다시 시도해주세요.")
            }

            val applicantList = lecturePersistencePort.findApplicantsByLecture(lectureApplicantDto.lectureId)
            val isApplied = applicantList.find { it.memberId == lectureApplicantDto.memberId } != null
            if (isApplied) {
                throw KstdException("이미 신청한 강의입니다.")
            }

            val lectureDto = lecturePersistencePort.findLecture(lectureApplicantDto.lectureId) ?: return
            if (lectureDto.applicantLimit <= applicantList.size) {
                throw KstdException("정원 초과 되었습니다.")
            }

            lecturePersistencePort.saveApplicant(lectureApplicantDto)
        } catch (e: Exception) {
            throw e
        } finally {
            cachePort.unlock(LOCK_KEY)
        }
    }

    override fun cancelLecture(lectureApplicantDto: LectureApplicantDto) {
        lecturePersistencePort.removeApplicant(lectureApplicantDto)
    }
}
