package kr.hope.springboot.util.aop.hook

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Aspect
@Component
class HookAspect(
    private val context: ApplicationContext,
) {
    @Around("@annotation(kr.hope.springboot.util.aop.hook.Hook)")
    fun hook(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val args = joinPoint.args

        val hook = signature.method.getAnnotation(Hook::class.java)
        val instance = context.getBean(hook.clazz.java)

        return when (hook.type) {
            Hook.Type.BEFORE -> {
                instance.call(args)
                joinPoint.proceed()
            }
            Hook.Type.AFTER -> {
                val result = joinPoint.proceed()
                instance.call(args, result)
                result
            }
        }
    }

    @Around("@annotation(kr.hope.springboot.util.aop.hook.Hooks)")
    fun hooks(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val args = joinPoint.args

        val hooks = signature.method.getAnnotation(Hooks::class.java).value
        val (beforeHooks, afterHooks) = hooks.partition { it.type == Hook.Type.BEFORE }

        beforeHooks.forEach { hook ->
            val instance = context.getBean(hook.clazz.java)
            instance.call(args)
        }

        val result = joinPoint.proceed()

        afterHooks.forEach { hook ->
            val instance = context.getBean(hook.clazz.java)
            instance.call(args, result)
        }

        return result
    }
}
