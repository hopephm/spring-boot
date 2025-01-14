package kr.hope.springboot.jpa

import io.mockk.junit5.MockKExtension
import kr.hope.springboot.jpa.config.TestConfig
import kr.hope.springboot.jpa.domain.board.BoardEntity
import kr.hope.springboot.jpa.domain.board.BoardEntityDslRepository
import kr.hope.springboot.jpa.domain.board.BoardEntityRepository
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
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class, MockKExtension::class)
@ContextConfiguration(classes = [TestConfig::class])
@TestPropertySource(locations = ["classpath:application-test.yml"])
class JpaIntegrationTest {
    @Autowired
    private lateinit var boardEntityRepository: BoardEntityRepository

    @Test
    fun jpaStoreTest() {
        // given
        val boardEntity = BoardEntity(
            title = "title",
            content = "content",
        )

        // when
        boardEntityRepository.save(boardEntity)

        // then
        assertThat(boardEntity.id).isNotNull

        val storedEntity = boardEntityRepository.findByIdOrNull(boardEntity.id)
        assertThat(storedEntity).isNotNull
        assertThat(storedEntity?.title).isEqualTo(boardEntity.title)
        assertThat(storedEntity?.content).isEqualTo(boardEntity.content)
    }

    @Test
    fun queryDslStoreTest() {
        // given
        val boardEntity = BoardEntity(
            title = "title",
            content = "content",
        )
        boardEntityRepository.save(boardEntity)

        // when
        val searchCondition = BoardEntityDslRepository.SearchCondition(
            titles = listOf("title"),
        )
        val pageable = PageRequest.of(0, 10)
        val result = boardEntityRepository.findAllByCondition(searchCondition, pageable)

        // then
        assertThat(result.totalPages).isEqualTo(1)
        assertThat(result.totalElements).isEqualTo(1)
        assertThat(result.numberOfElements).isEqualTo(1)
        assertThat(result.size).isEqualTo(10)
        assertThat(result.content[0].title).isEqualTo(boardEntity.title)
        assertThat(result.content[0].content).isEqualTo(boardEntity.content)
    }
}
