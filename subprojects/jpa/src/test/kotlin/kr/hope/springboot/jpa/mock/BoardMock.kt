package kr.hope.springboot.jpa.mock

import kr.hope.springboot.jpa.domain.board.Board
import kr.hope.springboot.jpa.domain.member.Member

object BoardMock {
    object Default {
        val title = "title"
        val content = "content"

        fun instance(
            creator: Member = MemberMock.Default.instance(),
        ): Board {
            return Board(
                title = title,
                content = content,
                creator = creator,
            )
        }
    }
}
