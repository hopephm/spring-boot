package kr.hope.springboot.jpa.integration

import io.mockk.junit5.MockKExtension
import jakarta.persistence.EntityManager
import kr.hope.springboot.jpa.config.DefaultConfig
import kr.hope.springboot.jpa.domain.account.AccountRepository
import kr.hope.springboot.jpa.domain.board.BoardRepository
import kr.hope.springboot.jpa.domain.member.MemberRepository
import kr.hope.springboot.jpa.mock.AccountMock
import kr.hope.springboot.jpa.mock.BoardMock
import kr.hope.springboot.jpa.mock.MemberMock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
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
class AssociationTest {
    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var boardRepository: BoardRepository

    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Test
    fun manyToOneTest() {
        // given
        val member = MemberMock.Default.instance()
        val board = BoardMock.Default.instance(creator = member)
        memberRepository.save(member)
        boardRepository.save(board)
        entityManager.clear()

        // when
        val storedBoard = boardRepository.findByIdOrNull(board.id)

        // then
        assertThat(storedBoard).isNotNull
        assertThat(storedBoard?.creator).isNotNull
        assertThat(storedBoard?.creator?.id).isEqualTo(member.id)
        assertThat(storedBoard?.creator?.nickname).isEqualTo(member.nickname)
    }

    @Test
    fun oneToManyTest() {
        // given
        val member = MemberMock.Default.instance()
        val board = BoardMock.Default.instance(creator = member)
        memberRepository.save(member)
        boardRepository.save(board)
        entityManager.clear()

        // when
        val storedMember = memberRepository.findByIdOrNull(member.id)

        // then
        assertThat(storedMember).isNotNull
        assertThat(storedMember?.boards).isNotEmpty
        assertThat(storedMember?.boards?.values?.firstOrNull()?.id).isEqualTo(board.id)
        assertThat(storedMember?.boards?.values?.firstOrNull()?.title).isEqualTo(board.title)
        assertThat(storedMember?.boards?.values?.firstOrNull()?.content).isEqualTo(board.content)
    }

    @Test
    fun oneToOneTest() {
        // given
        val member = MemberMock.Default.instance()
        val account = AccountMock.Default.instance(member)

        memberRepository.save(member)
        accountRepository.save(account)
        entityManager.clear()

        // when
        val storedMember = memberRepository.findByIdOrNull(member.id)
        val storedAccount = accountRepository.findByIdOrNull(account.id)

        // then
        assertThat(storedMember).isNotNull
        assertThat(storedMember?.account).isNotNull
        assertThat(storedMember?.account?.id).isEqualTo(account.id)
        assertThat(storedMember?.account?.loginId).isEqualTo(account.loginId)
        assertThat(storedMember?.account?.password).isEqualTo(account.password)

        assertThat(storedAccount).isNotNull
        assertThat(storedAccount?.member).isNotNull
        assertThat(storedAccount?.member?.id).isEqualTo(member.id)
        assertThat(storedAccount?.member?.nickname).isEqualTo(member.nickname)
    }

    @Test
    fun embeddedTest() {
        // given
        val member = MemberMock.Default.instance()
        member.personalInformation = MemberMock.Personal.Default.instance()
        memberRepository.save(member)
        entityManager.clear()

        // when
        val storedMember = memberRepository.findByIdOrNull(member.id)

        // then
        assertThat(storedMember).isNotNull
        assertThat(storedMember?.personalInformation).isNotNull
        assertThat(storedMember?.personalInformation?.name).isEqualTo(member.personalInformation?.name)
        assertThat(storedMember?.personalInformation?.age).isEqualTo(member.personalInformation?.age)
        assertThat(storedMember?.personalInformation?.phone).isEqualTo(member.personalInformation?.phone)
    }
}
