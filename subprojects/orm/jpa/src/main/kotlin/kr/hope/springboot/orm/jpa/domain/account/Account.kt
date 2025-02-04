package kr.hope.springboot.orm.jpa.domain.account

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import kr.hope.springboot.orm.jpa.common.BaseEntity
import kr.hope.springboot.orm.jpa.domain.member.Member

@Entity(name = "account")
class Account(
    var loginId: String,
    var password: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,
) : BaseEntity<Int>()
