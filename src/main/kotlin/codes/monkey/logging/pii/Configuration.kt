package codes.monkey.logging.pii

import codes.monkey.logging.pii.Configuration.ConfigKeys.DATA_MASKING_FUNCTION
import codes.monkey.logging.pii.Configuration.ConfigKeys.DEFAULT_DATA_MASKING_REPLACE_WITH
import codes.monkey.logging.pii.Configuration.ConfigKeys.PACKAGE_FILTER
import java.util.Properties


object Configuration {

    val properties: Properties


    enum class ConfigKeys(val key: String) {
        PACKAGE_FILTER("pii.filter.packages.contains"),
        DATA_MASKING_FUNCTION("pii.data.masking.function"),
        DEFAULT_DATA_MASKING_REPLACE_WITH("pii.data.masking.default.replacewith"),
    }

    init {
        val defaultProperties =  mapOf(
            PACKAGE_FILTER.key to "monkey",
            DATA_MASKING_FUNCTION.key to "codes.monkey.logging.pii.DefaultDataMaskingFunction",
            DEFAULT_DATA_MASKING_REPLACE_WITH.key to "***"
        ).toProperties()
        properties = Configuration::class.java.classLoader
            .getResourceAsStream("pii.properties")
            ?.let {
                defaultProperties.apply {
                    putAll(Properties().apply { load(it) })
                }
            }
            ?: defaultProperties
    }

    val dataMaskingFunction: DataMaskingFunction by lazy {
        Class.forName(properties.getProperty(ConfigKeys.DATA_MASKING_FUNCTION.key))
            .getDeclaredConstructor()
            .newInstance() as DataMaskingFunction
    }

    private val packageContains: String
        get() = properties.getProperty(PACKAGE_FILTER.key)

    val defaultMaskWith: String
        get() = properties.getProperty(DEFAULT_DATA_MASKING_REPLACE_WITH.key)

    fun accept(clazz: Class<*>) = clazz.name.contains(packageContains)

}