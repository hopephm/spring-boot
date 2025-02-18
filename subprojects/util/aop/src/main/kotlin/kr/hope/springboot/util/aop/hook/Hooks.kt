package kr.hope.springboot.util.aop.hook

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
internal annotation class Hooks(
    val value: Array<Hook>,
)
