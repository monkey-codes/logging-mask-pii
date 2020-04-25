package codes.monkey.logging.pii

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LoggingMaskPiiApplication

fun main(args: Array<String>) {
	runApplication<LoggingMaskPiiApplication>(*args)
}
