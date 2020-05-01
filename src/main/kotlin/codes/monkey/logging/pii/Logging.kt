package codes.monkey.logging.pii

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logging {
    inline fun <reified T : Any> loggerFor(): Logger = PIIMaskingLogger(LoggerFactory.getLogger(T::class.java))
    fun loggerForCompanion(c: Any): Logger = PIIMaskingLogger(LoggerFactory.getLogger(c.javaClass.enclosingClass))
}