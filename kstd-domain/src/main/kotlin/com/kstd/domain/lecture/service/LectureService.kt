package com.kstd.domain.lecture.service

import com.kstd.common.exception.KstdException
import com.kstd.domain.lecture.dto.LectureApplicantDto
import com.kstd.domain.lecture.dto.LectureCondition
import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.dto.LectureFormDto
import com.kstd.domain.lecture.policy.LecturePolicy
import com.kstd.domain.lecture.port.input.LectureUseCase
import com.kstd.domain.lecture.port.output.LecturePersistencePort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
internal class LectureService(
    private val lecturePersistencePort: LecturePersistencePort,
): LectureUseCase {
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

    override fun saveLecture(form: LectureFormDto): LectureDto {
        return lecturePersistencePort.saveLecture(form)
    }

    override fun findApplicants(lectureId: Long): List<String> {
        return lecturePersistencePort.findApplicants(lectureId)
            .map { it.memberId }
    }

    override fun applyLecture(lectureApplicantDto: LectureApplicantDto) {
        if (lectureApplicantDto.isNotValidMemberId()) {
            throw KstdException("사번이 유효하지 않습니다.")
        }

        // TODO: Lock 필요
        val applicantList = lecturePersistencePort.findApplicants(lectureApplicantDto.lectureId)
        val isApplied = applicantList.find { it.memberId == lectureApplicantDto.memberId } != null
        if (isApplied) {
            throw KstdException("이미 신청한 강의입니다.")
        }

        val lectureDto = lecturePersistencePort.findLecture(lectureApplicantDto.lectureId) ?: return
        if (lectureDto.applicantLimit <= applicantList.size) {
            throw KstdException("정원 초과 되었습니다.")
        }

        lecturePersistencePort.saveApplicants(lectureApplicantDto)
    }
}