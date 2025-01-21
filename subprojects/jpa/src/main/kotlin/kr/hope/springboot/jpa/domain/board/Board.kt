package kr.hope.springboot.jpa.domain.board

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kr.hope.springboot.jpa.common.BaseEntity
import kr.hope.springboot.jpa.domain.member.Member

@Entity(name = "board")
class Board(
    var title: String,
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    var creator: Member,
) : BaseEntity<Int>()
