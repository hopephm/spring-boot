package kr.hope.springboot.jpa.integration

import io.mockk.junit5.MockKExtension
import jakarta.persistence.EntityManager
import kr.hope.springboot.jpa.config.DefaultConfig
import kr.hope.springboot.jpa.domain.board.BoardDslRepository
import kr.hope.springboot.jpa.domain.board.BoardRepository
import kr.hope.springboot.jpa.domain.member.MemberRepository
import kr.hope.springboot.jpa.mock.BoardMock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataJpaTest
@ActiveProfiles(DefaultConfig.DEFAULT_PROFILES)
@ExtendWith(SpringExtension::class, MockKExtension::class)
@ContextConfiguration(classes = [DefaultConfig::class])
@TestPropertySource(locations = [DefaultConfig.TEST_PROPERTY_SOURCE])
class JpaStoreTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var boardRepository: BoardRepository

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    fun jpaStoreTest() {
        // given
        val board = BoardMock.Default.instance()
        entityManager.clear()

        // when
        boardRepository.save(board)

        // then
        assertThat(board.id).isNotNull

        val storedEntity = boardRepository.findByIdOrNull(board.id)
        assertThat(storedEntity).isNotNull
        assertThat(storedEntity?.title).isEqualTo(board.title)
        assertThat(storedEntity?.content).isEqualTo(board.content)
    }

    @Test
    fun queryDslStoreTest() {
        // given
        val board = BoardMock.Default.instance()
        memberRepository.save(board.creator)
        boardRepository.save(board)
        entityManager.clear()

        // when
        val searchCondition = BoardDslRepository.SearchCondition(
            titles = listOf(board.title),
        )
        val pageable = PageRequest.of(0, 10)
        val result = boardRepository.findAllByCondition(searchCondition, pageable)

        // then
        assertThat(result.totalPages).isEqualTo(1)
        assertThat(result.totalElements).isEqualTo(1)
        assertThat(result.numberOfElements).isEqualTo(1)
        assertThat(result.size).isEqualTo(10)
        assertThat(result.content[0].title).isEqualTo(board.title)
        assertThat(result.content[0].content).isEqualTo(board.content)
    }
}
