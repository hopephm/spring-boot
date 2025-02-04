package kr.hope.springboot.orm.jpa.domain.member

import jakarta.persistence.Embeddable

@Embeddable
data class PersonalInformation(
    val name: String,
    val age: Int,
    val phone: String,
)
