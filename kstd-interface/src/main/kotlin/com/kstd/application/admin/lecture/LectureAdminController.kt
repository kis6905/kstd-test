package com.kstd.application.admin.lecture

import com.kstd.application.admin.KstdAdminController
import com.kstd.domain.lecture.dto.LectureDto
import com.kstd.domain.lecture.dto.LectureFormDto
import com.kstd.domain.lecture.port.input.LectureUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@KstdAdminController
class LectureAdminController(
    private val lectureUseCase: LectureUseCase
) {

    @GetMapping("/lectures")
    fun getLectures(): List<LectureDto> {
        return lectureUseCase.findAllLectureList()
    }

    @PostMapping("/lecture")
    fun postLecture(@RequestBody lectureFormDto: LectureFormDto): LectureDto {
        return lectureUseCase.saveLecture(lectureFormDto)
    }

    @GetMapping("/applicants/by/{lectureId}")
    fun getApplicantsByLecture(@PathVariable lectureId: Long): List<String> {
        return lectureUseCase.findApplicants(lectureId)
    }
}
