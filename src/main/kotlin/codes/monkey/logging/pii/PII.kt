package codes.monkey.logging.pii

/**
 * Personal Identifiable fields can be annotated with @PII to mask it in logs
 */
@Target(allowedTargets = [AnnotationTarget.FIELD])
@Retention
annotation class PII