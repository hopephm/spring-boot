package kr.hope.springboot.util.aop.hook.mock.usage

import kr.hope.springboot.util.aop.hook.Hook
import kr.hope.springboot.util.aop.hook.mock.util.MockOutput
import kr.hope.springboot.util.aop.hook.mock.impl.AccessLogHook
import kr.hope.springboot.util.aop.hook.mock.impl.GoodByeHook
import kr.hope.springboot.util.aop.hook.mock.impl.HelloUserHook

open class LoginService {
    @Hook(AccessLogHook::class, Hook.Type.BEFORE)
    @Hook(HelloUserHook::class, Hook.Type.AFTER)
    open fun login(
        id: String,
        password: String,
    ) {
        MockOutput.print("logged in")
    }

    @Hook(GoodByeHook::class, Hook.Type.BEFORE)
    open fun logout() {
        MockOutput.print("logged out")
    }
}
