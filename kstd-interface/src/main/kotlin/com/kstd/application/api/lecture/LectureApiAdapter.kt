package com.kstd.application.api.lecture

import com.kstd.application.api.KstdApiV1Controller
import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.port.input.LectureUseCase
import org.springframework.web.bind.annotation.GetMapping

@KstdApiV1Controller
class LectureApiAdapter(
    private val lectureUseCase: LectureUseCase
) {

    @GetMapping("/test")
    fun test(): List<LectureDto> {
        return lectureUseCase.findLectureList()
    }

}