package codes.monkey.logging.pii

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

@Aspect
class PIIAspect {

    @Around("execution (* codes.monkey.logging..toString())")
    fun trace(joinPoint: ProceedingJoinPoint): Any {
        return joinPoint
            .target::class.java.declaredFields
            .firstOrNull { it.getAnnotation(PII::class.java) != null }
            ?.let {
                PIIMaskingToStringBuilder.toString(joinPoint.target)
            } ?: joinPoint.proceed()
    }

}