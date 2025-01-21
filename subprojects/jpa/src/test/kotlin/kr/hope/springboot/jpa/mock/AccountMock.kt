package kr.hope.springboot.jpa.mock

import kr.hope.springboot.jpa.domain.account.Account
import kr.hope.springboot.jpa.domain.member.Member

object AccountMock {
    object Default {
        val loginId = "loginId"
        val password = "password"

        fun instance(
            member: Member = MemberMock.Default.instance(),
        ): Account {
            return Account(
                loginId = loginId,
                password = password,
                member = member,
            )
        }
    }
}
