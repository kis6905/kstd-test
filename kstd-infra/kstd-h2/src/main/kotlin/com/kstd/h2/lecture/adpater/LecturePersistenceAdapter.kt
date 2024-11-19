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
import kotlin.jvm.optionals.getOrNull

@Component
class LecturePersistenceAdapter(
    private val lectureRepository: LectureRepository,
    private val lectureApplicantRepository: LectureApplicantRepository,
): LecturePersistencePort {
    override fun findLectureList(condition: LectureCondition): List<LectureDto> {
        return lectureRepository.findList(condition)
            .toLectureDto()
    }

    override fun findLecture(lectureId: Long): LectureDto? {
        return lectureRepository.findById(lectureId).getOrNull()
            ?.toLectureDto()
    }

    override fun saveLecture(form: LectureFormDto): LectureDto {
        return lectureRepository.save(form.toLecture())
            .toLectureDto()
    }

    override fun findApplicants(lectureId: Long): List<LectureApplicantDto> {
        return lectureApplicantRepository.findByLectureId(lectureId)
            .toLectureApplicantDto()
    }

    override fun findApplicant(lectureId: Long, memberId: String): LectureApplicantDto? {
        return lectureApplicantRepository.findByLectureIdAndMemberId(lectureId, memberId)
            ?.toLectureApplicantDto()
    }

    override fun saveApplicants(lectureApplicantDto: LectureApplicantDto): LectureApplicantDto {
        return lectureApplicantRepository.save(lectureApplicantDto.toLectureApplicant())
            .toLectureApplicantDto()
    }
}
