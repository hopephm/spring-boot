package kr.hope.springboot.util.aop.hook.mock.impl

import kr.hope.springboot.util.aop.hook.HookCall
import kr.hope.springboot.util.aop.hook.mock.util.MockOutput

class GoodByeHook : HookCall {
    override fun call(args: Array<Any?>, result: Any?) {
        MockOutput.print("Goodbye! see you later!")
    }
}
