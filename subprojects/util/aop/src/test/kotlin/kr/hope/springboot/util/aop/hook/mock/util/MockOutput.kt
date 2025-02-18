package kr.hope.springboot.util.aop.hook.mock.util

object MockOutput {
    val log = mutableListOf<String>()

    fun print(msg: String) {
        log.add(msg)
    }

    fun clear() {
        log.clear()
    }
}
