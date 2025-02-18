package kr.hope.springboot.util.aop.hook

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@JvmRepeatable(Hooks::class)
annotation class Hook(
    val clazz: KClass<out HookCall>,
    val type: Type = Type.BEFORE,
) {
    enum class Type {
        BEFORE,
        AFTER,
    }
}
