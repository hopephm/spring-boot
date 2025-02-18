package kr.hope.springboot.util.aop.hook

import kr.hope.springboot.util.aop.hook.mock.config.HookConfig
import kr.hope.springboot.util.aop.hook.mock.usage.LoginService
import kr.hope.springboot.util.aop.hook.mock.util.MockOutput
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class HookAspectTest {
    private lateinit var applicationContext: AnnotationConfigApplicationContext

    @BeforeEach
    fun init() {
        applicationContext = AnnotationConfigApplicationContext()
        applicationContext.register(HookConfig::class.java)
        applicationContext.refresh()
    }

    @AfterEach
    fun clear() {
        applicationContext.close()
        MockOutput.clear()
    }

    /**
     * @Hook 어노테이션이 여러 개 붙은 함수가 호출되면 각 HookCall 구현체가 호출되는지 확인한다.
     * Repeatable annotation 테스트로, HookAspect의 hooks 함수가 호출된다.
     * - 로그인 전 접근 기록을 남기고, 로그인 후 환영인사를 출력한다.
     */
    @Test
    fun hooksTest() {
        // given
        val loginService = applicationContext.getBean(LoginService::class.java)

        val id = "mang"
        val password = "mang1234"

        // when
        loginService.login(id, password)

        // then
        assertThat(MockOutput.log).containsExactly(
            "access with (id: mang, password: m******4)",
            "logged in",
            "Hello, mang! welcome to the Hook!"
        )
    }

    /**
     * @Hook 어노테이션이 붙은 함수가 호출되면 HookCall 구현체가 호출되는지 확인한다.
     * - HookAspect의 hook 함수가 호출된다.
     * - 로그아웃 전 작별인사를 출력한다.
     */
    @Test
    fun hookTest() {
        // given
        val loginService = applicationContext.getBean(LoginService::class.java)

        // when
        loginService.logout()

        // then
        assertThat(MockOutput.log).containsExactly(
            "Goodbye! see you later!",
            "logged out"
        )
    }
}
