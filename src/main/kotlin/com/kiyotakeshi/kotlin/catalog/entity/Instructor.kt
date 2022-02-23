package com.kiyotakeshi.kotlin.catalog.entity

import javax.persistence.*

@Entity
@Table(name = "instructors")
data class Instructor(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,

    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var course: List<Course> = mutableListOf()
)
