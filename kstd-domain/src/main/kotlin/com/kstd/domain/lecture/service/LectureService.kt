package com.kstd.domain.lecture.service

import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.port.input.LectureUseCase
import com.kstd.domain.lecture.port.output.LecturePersistencePort
import org.springframework.stereotype.Service

@Service
internal class LectureService(
    private val lecturePersistencePort: LecturePersistencePort,
): LectureUseCase {
    override fun findLectureList(): List<LectureDto> {
        return lecturePersistencePort.findLectureList()
    }
}