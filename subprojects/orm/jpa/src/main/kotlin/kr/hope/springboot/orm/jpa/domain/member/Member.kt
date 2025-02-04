package kr.hope.springboot.orm.jpa.domain.member

import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.MapKey
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import kr.hope.springboot.orm.jpa.common.BaseEntity
import kr.hope.springboot.orm.jpa.domain.account.Account
import kr.hope.springboot.orm.jpa.domain.board.Board

@Entity(name = "member")
class Member(
    var nickname: String,
) : BaseEntity<Int>() {
    @OneToMany(mappedBy = "creator", cascade = [CascadeType.ALL], orphanRemoval = true)
    @MapKey(name = "id")
    val boards: MutableMap<Int, Board> = mutableMapOf()

    @OneToOne(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var account: Account? = null

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "name", column = Column(name = "member_name")),
        AttributeOverride(name = "age", column = Column(name = "member_age")),
        AttributeOverride(name = "phone", column = Column(name = "member_phone_number"))
    )
    var personalInformation: PersonalInformation? = null
}
