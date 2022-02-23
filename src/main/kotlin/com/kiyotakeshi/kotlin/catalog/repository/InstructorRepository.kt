package com.kiyotakeshi.kotlin.catalog.repository

import com.kiyotakeshi.kotlin.catalog.entity.Instructor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InstructorRepository : CrudRepository<Instructor, Int>
