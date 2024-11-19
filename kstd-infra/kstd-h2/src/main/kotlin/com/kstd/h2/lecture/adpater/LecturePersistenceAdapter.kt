package com.kstd.h2.lecture.adpater

import com.kstd.domain.lecture.dto.LectureApplicantDto
import com.kstd.domain.lecture.dto.LectureCondition
import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.dto.LectureFormDto
import com.kstd.domain.lecture.port.output.LecturePersistencePort
import com.kstd.h2.lecture.converter.toLecture
import com.kstd.h2.lecture.converter.toLectureApplicant
import com.kstd.h2.lecture.converter.toLectureApplicantDto
import com.kstd.h2.lecture.converter.toLectureDto
import com.kstd.h2.lecture.repository.LectureApplicantRepository
import com.kstd.h2.lecture.repository.LectureRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Component
@Transactional
class LecturePersistenceAdapter(
    private val lectureRepository: LectureRepository,
    private val lectureApplicantRepository: LectureApplicantRepository,
): LecturePersistencePort {
    @Transactional(readOnly = true)
    override fun findLectureList(condition: LectureCondition): List<LectureDto> {
        return lectureRepository.findList(condition)
            .toLectureDto()
    }

    @Transactional(readOnly = true)
    override fun findLecture(lectureId: Long): LectureDto? {
        return lectureRepository.findById(lectureId).getOrNull()
            ?.toLectureDto()
    }

    override fun saveLecture(form: LectureFormDto): LectureDto {
        return lectureRepository.save(form.toLecture())
            .toLectureDto()
    }

    @Transactional(readOnly = true)
    override fun findApplicantsByLecture(lectureId: Long): List<LectureApplicantDto> {
        return lectureApplicantRepository.findByLectureId(lectureId)
            .toLectureApplicantDto()
    }

    @Transactional(readOnly = true)
    override fun findApplicantsByMember(memberId: String): List<LectureApplicantDto> {
        return lectureApplicantRepository.findByMemberId(memberId)
            .toLectureApplicantDto()
    }

    @Transactional(readOnly = true)
    override fun findApplicant(lectureId: Long, memberId: String): LectureApplicantDto? {
        return lectureApplicantRepository.findByLectureIdAndMemberId(lectureId, memberId)
            ?.toLectureApplicantDto()
    }

    override fun saveApplicant(applicant: LectureApplicantDto): LectureApplicantDto {
        return lectureApplicantRepository.save(applicant.toLectureApplicant())
            .toLectureApplicantDto()
    }

    override fun removeApplicant(applicant: LectureApplicantDto) {
        val entity = lectureApplicantRepository.findByLectureIdAndMemberId(applicant.lectureId, applicant.memberId)
        entity?.let {
            lectureApplicantRepository.delete(it)
        }
    }
}
