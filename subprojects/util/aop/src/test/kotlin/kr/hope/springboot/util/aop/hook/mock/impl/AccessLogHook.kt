package kr.hope.springboot.util.aop.hook.mock.impl

import kr.hope.springboot.util.aop.hook.HookCall
import kr.hope.springboot.util.aop.hook.mock.util.MockOutput

class AccessLogHook : HookCall {
    override fun call(args: Array<Any?>, result: Any?) {
        validateArguments(args)
        val id = args[0] as String
        val password = args[1] as String
        MockOutput.print("access with (id: $id, password: ${password.mask()})")
    }

    private fun validateArguments(args: Array<Any?>) {
        if (args.size != 2) {
            throw IllegalArgumentException("LoginService.login must have 2 arguments")
        }
        val id = args[0]
        if (id !is String || id.isBlank()) {
            throw IllegalArgumentException("LoginService.login first argument must be String")
        }

        val password = args[1]
        if (password !is String || password.isBlank()) {
            throw IllegalArgumentException("LoginService.login second argument must be String")
        }
    }

    private fun String.mask(): String {
        return this.first() + "*".repeat(this.length - 2) + this.last()
    }
}
