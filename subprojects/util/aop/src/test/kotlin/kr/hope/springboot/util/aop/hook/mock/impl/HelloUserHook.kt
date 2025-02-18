package kr.hope.springboot.util.aop.hook.mock.impl

import kr.hope.springboot.util.aop.hook.HookCall
import kr.hope.springboot.util.aop.hook.mock.util.MockOutput

class HelloUserHook : HookCall {
    override fun call(args: Array<Any?>, result: Any?) {
        validateArguments(args)
        val id = args[0] as String
        MockOutput.print("Hello, $id! welcome to the Hook!")
    }

    private fun validateArguments(args: Array<Any?>) {
        val id = args[0]
        if (id !is String) {
            throw IllegalArgumentException("HelloService.hello first argument must be String")
        }
    }
}
