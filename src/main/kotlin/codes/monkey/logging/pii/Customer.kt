package codes.monkey.logging.pii

import java.time.LocalDate
import java.util.UUID

data class Customer(
    val id: UUID,
    @PII val name: String,
    val dob: LocalDate,
    @PII val email: String,
    @PII val socialSecurityNumber: String,
    @PII val driversLicenseNumber: String,
    @PII val passportNumber: String
)
