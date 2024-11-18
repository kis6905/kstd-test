package com.kstd.h2.lecture.adpater

import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.port.output.LecturePersistencePort
import com.kstd.h2.lecture.converter.toLectureDto
import com.kstd.h2.lecture.repository.LectureRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class LecturePersistenceAdapter(
    private val lectureRepository: LectureRepository,
): LecturePersistencePort {
    override fun findLectureList(): List<LectureDto> {
        return lectureRepository.findAll()
            .toLectureDto()
    }

}