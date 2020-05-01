package codes.monkey.logging.pii


import assertk.assertions.isEqualTo
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import java.util.UUID
import java.util.stream.Stream
import org.apache.commons.lang3.builder.ToStringExclude
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.Logger

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PIIMaskingLoggerTest {

    lateinit var logger: Logger
    lateinit var listAppender: ListAppender<ILoggingEvent>

    @BeforeAll
    fun setup() {
        logger = Logging.loggerFor<PIIMaskingLoggerTest>()
        listAppender = ListAppender()
        listAppender.start()
        ((logger as PIIMaskingLogger).logger as ch.qos.logback.classic.Logger).apply {
            addAppender(listAppender)
        }
    }

    @BeforeEach
    fun beforeEach() {
        listAppender.list.clear()
    }

    @Test
    fun `it should mask PII`() {
        val beanWithPII = BeanWithPII().apply { recursiveReference = this }
        logger.info("Handling {}", beanWithPII)
        assertk.assert(logMessage())
            .isEqualTo("Handling BeanWithPII(id=e7774630-e2d5-4b20-9c8e-3437a4912640, accountNumber=1233456, prop1=prop1, piiProp=***, listOfPII=***)")
    }

    private fun logMessage() = listAppender.list.first().formattedMessage

    @ParameterizedTest
    @MethodSource("simpleArgs")
    fun `it should deal primitives`(value: Any) {
        logger.info("Handling {}", value)
        assertk.assert(logMessage())
            .isEqualTo("Handling $value")
    }

    @Test
    fun `it should deal with nulls`() {
        logger.info("Handling {}", null)
        assertk.assert(logMessage())
            .isEqualTo("Handling {}")
    }

    companion object {
        @JvmStatic
        fun simpleArgs(): Stream<Arguments> =
            Stream.of(
                Arguments.of(1),
                Arguments.of(1L),
                Arguments.of(1.234f),
                Arguments.of("a string"),
                Arguments.of(false),
                Arguments.of(UUID.randomUUID())
            )
    }
}

class BeanWithPII(
    val id: UUID = UUID.fromString("e7774630-e2d5-4b20-9c8e-3437a4912640"),
    val accountNumber: Long = 1233456L,
    val prop1: String = "prop1",
    @PII
    val piiProp: String = "piiProp1",
    @ToStringExclude
    var recursiveReference: BeanWithPII? = null,
    @PII
    val listOfPII: List<String> = listOf("secret", "secret")
)