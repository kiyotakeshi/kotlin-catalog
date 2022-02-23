package com.kiyotakeshi.kotlin.catalog.service

import com.kiyotakeshi.kotlin.catalog.dto.InstructorDto
import com.kiyotakeshi.kotlin.catalog.entity.Instructor
import com.kiyotakeshi.kotlin.catalog.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class InstructorService(
    val instructorRepository: InstructorRepository
) {
    fun createInstructor(instructorDto: InstructorDto): InstructorDto {
        val instructorEntity = instructorDto.let {
            Instructor(it.id, it.name)
        }
        instructorRepository.save(instructorEntity)

        return instructorEntity.let {
            InstructorDto(it.id, it.name)
        }
    }

    fun findByInstructorId(instructorId: Int): Optional<Instructor> {
        return instructorRepository.findById(instructorId)
    }
}
