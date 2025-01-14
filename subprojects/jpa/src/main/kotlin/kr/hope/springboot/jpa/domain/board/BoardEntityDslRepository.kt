package kr.hope.springboot.jpa.domain.board

import kr.hope.springboot.jpa.domain.board.QBoardEntity.boardEntity
import kr.hope.springboot.jpa.querydsl.DomainQuerydslSupport
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardEntityDslRepository {
    fun findAllByCondition(
        condition: SearchCondition,
        pageable: Pageable,
    ): Page<BoardEntity>

    data class SearchCondition(
        val titles: List<String>,
    )
}

class BoardEntityDslRepositoryImpl : BoardEntityDslRepository, DomainQuerydslSupport(BoardEntity::class) {
    override fun findAllByCondition(
        condition: BoardEntityDslRepository.SearchCondition,
        pageable: Pageable,
    ): Page<BoardEntity> {
        return getJpaQueryFactory()
            .selectFrom(boardEntity)
            .where(
                boardEntity.title.`in`(condition.titles),
            ).fetchPage(pageable)
    }
}
