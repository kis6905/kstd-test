package com.kstd.application.api.lecture

import com.kstd.application.api.KstdApiV1Controller
import com.kstd.domain.lecture.dto.LectureApplicantDto
import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.port.input.LectureUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@KstdApiV1Controller
class LectureApiController(
    private val lectureUseCase: LectureUseCase
) {

    @GetMapping("/lectures")
    fun getLectures(): List<LectureDto> {
        return lectureUseCase.findApplicableLectureList()
    }

    @PostMapping("/applicant")
    fun postApplicant(@RequestBody lectureApplicantDto: LectureApplicantDto) {
        lectureUseCase.applyLecture(lectureApplicantDto)
    }
}
