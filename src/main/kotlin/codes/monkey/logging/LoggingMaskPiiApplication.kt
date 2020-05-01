package codes.monkey.logging

import codes.monkey.logging.pii.Customer
import codes.monkey.logging.pii.Logging
import codes.monkey.logging.pii.PII
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate
import java.util.UUID
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

@SpringBootApplication
class LoggingMaskPiiApplication : CommandLineRunner {
    companion object {
        val LOGGER = Logging.loggerForCompanion(this)

    }
    override fun run(vararg args: String?) {

        val customer = Customer(
            id = UUID.randomUUID(),
            name = "Monkey Codes",
            dob = LocalDate.EPOCH,
            email = "null@localhost.com",
            socialSecurityNumber = "111-22-3333",
            driversLicenseNumber = "123456",
            passportNumber = "98765432"
        )
        LOGGER.info("a log message with PII string interpolation $customer")
        LOGGER.info("a template message with PII {}", customer)
        println("println without logging framework $customer")
    }

}

fun main(args: Array<String>) {
    runApplication<LoggingMaskPiiApplication>(*args)
}
