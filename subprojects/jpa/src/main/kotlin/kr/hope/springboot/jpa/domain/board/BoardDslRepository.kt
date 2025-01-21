package kr.hope.springboot.jpa.domain.board

import kr.hope.springboot.jpa.domain.account.QAccount.account
import kr.hope.springboot.jpa.domain.board.QBoard.board
import kr.hope.springboot.jpa.domain.member.QMember.member
import kr.hope.springboot.jpa.querydsl.DomainQuerydslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardDslRepository {
    fun findAllByCondition(
        condition: SearchCondition,
        pageable: Pageable,
    ): Page<Board>

    data class SearchCondition(
        val titles: List<String>,
    )
}

class BoardDslRepositoryImpl : BoardDslRepository, DomainQuerydslSupport(Board::class) {
    override fun findAllByCondition(
        condition: BoardDslRepository.SearchCondition,
        pageable: Pageable,
    ): Page<Board> {
        return getJpaQueryFactory()
            .selectFrom(board)
            .leftJoin(board.creator, member).fetchJoin()
            .leftJoin(member.account, account).fetchJoin()
            .where(
                board.title.`in`(condition.titles),
            ).fetchPage(pageable)
    }
}
