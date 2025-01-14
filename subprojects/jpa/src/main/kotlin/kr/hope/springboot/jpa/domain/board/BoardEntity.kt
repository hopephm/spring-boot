package kr.hope.springboot.jpa.domain.board

import jakarta.persistence.Entity
import kr.hope.springboot.jpa.common.BaseEntity

@Entity(name = "board")
class BoardEntity(
    var title: String,
    var content: String,
) : BaseEntity<Int>()
