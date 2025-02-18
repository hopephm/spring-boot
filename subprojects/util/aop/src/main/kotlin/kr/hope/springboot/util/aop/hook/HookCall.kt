package kr.hope.springboot.util.aop.hook

interface HookCall {
    fun call(args: Array<Any?>, result: Any? = null)
}
