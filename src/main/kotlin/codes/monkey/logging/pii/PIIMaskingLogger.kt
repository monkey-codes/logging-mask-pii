package codes.monkey.logging.pii

import org.slf4j.Logger
import org.slf4j.Marker

class PIIMaskingLogger(val logger: Logger) : Logger by logger {

    override fun error(format: String?, vararg arguments: Any?) = logger.error(format, *maskAll(arguments))

    override fun error(marker: Marker?, format: String?, vararg arguments: Any?) =
        logger.error(marker, format, *maskAll(arguments))

    override fun error(format: String?, arg: Any?) = logger.error(format, mask(arg))

    override fun error(format: String?, arg1: Any?, arg2: Any?) = logger.error(format, mask(arg1), mask(arg2))

    override fun warn(format: String?, vararg arguments: Any?) = logger.warn(format, *maskAll(arguments))

    override fun warn(marker: Marker?, format: String?, vararg arguments: Any?) =
        logger.warn(marker, format, *maskAll(arguments))

    override fun warn(format: String?, arg: Any?) = logger.warn(format, mask(arg))

    override fun warn(format: String?, arg1: Any?, arg2: Any?) = logger.warn(format, mask(arg1), mask(arg2))

    override fun info(format: String?, vararg arguments: Any?) = logger.info(format, *maskAll(arguments))

    override fun info(marker: Marker?, format: String?, vararg arguments: Any?) =
        logger.info(marker, format, *maskAll(arguments))

    override fun info(format: String?, arg: Any?) = logger.info(format, mask(arg))

    override fun info(format: String?, arg1: Any?, arg2: Any?) = logger.info(format, mask(arg1), mask(arg2))

    override fun debug(format: String?, vararg arguments: Any?) = logger.debug(format, *maskAll(arguments))

    override fun debug(marker: Marker?, format: String?, vararg arguments: Any?) =
        logger.debug(marker, format, *maskAll(arguments))

    override fun debug(format: String?, arg: Any?) = logger.debug(format, mask(arg))

    override fun debug(format: String?, arg1: Any?, arg2: Any?) = logger.debug(format, mask(arg1), mask(arg2))

    override fun trace(format: String?, vararg arguments: Any?) = logger.trace(format, *maskAll(arguments))

    override fun trace(marker: Marker?, format: String?, vararg arguments: Any?) =
        logger.trace(marker, format, *maskAll(arguments))

    override fun trace(format: String?, arg: Any?) = logger.trace(format, mask(arg))

    override fun trace(format: String?, arg1: Any?, arg2: Any?) = logger.trace(format, mask(arg1), mask(arg2))

    private fun maskAll(arguments: Array<out Any?>) = arguments.map(this::mask).toTypedArray()
    private fun mask(arg: Any?): String? = when {
        arg == null -> arg
        Configuration.accept(arg::class.java) -> PIIMaskingToStringBuilder.toString(arg)
        else -> arg.toString()
    }
}