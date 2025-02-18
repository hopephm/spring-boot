package kr.hope.springboot.util.aop.hook.mock.config

import kr.hope.springboot.util.aop.hook.HookAspect
import kr.hope.springboot.util.aop.hook.mock.impl.AccessLogHook
import kr.hope.springboot.util.aop.hook.mock.impl.GoodByeHook
import kr.hope.springboot.util.aop.hook.mock.impl.HelloUserHook
import kr.hope.springboot.util.aop.hook.mock.usage.LoginService
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAspectJAutoProxy
class HookConfig {
    @Bean
    fun loginService(): LoginService {
        return LoginService()
    }

    @Bean
    fun logHookCall(): AccessLogHook {
        return AccessLogHook()
    }

    @Bean
    fun helloUserHookCall(): HelloUserHook {
        return HelloUserHook()
    }

    @Bean
    fun byeByeHookCall(): GoodByeHook {
        return GoodByeHook()
    }

    @Bean
    fun hookAspect(context: ApplicationContext): HookAspect {
        return HookAspect(context)
    }
}
