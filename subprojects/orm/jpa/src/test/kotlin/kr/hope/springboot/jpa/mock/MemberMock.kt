package kr.hope.springboot.jpa.mock

import kr.hope.springboot.orm.jpa.domain.member.Member
import kr.hope.springboot.orm.jpa.domain.member.PersonalInformation

object MemberMock {
    object Default {
        val nickname = "nickname"

        fun instance(): Member {
            return Member(
                nickname = nickname,
            )
        }
    }

    object Personal {
        object Default {
            val name = "name"
            val age = 20
            val phone = "01012345678"

            fun instance(): PersonalInformation {
                return PersonalInformation(
                    name = name,
                    age = age,
                    phone = phone,
                )
            }
        }
    }
}
