package codes.monkey.logging.pii


import java.lang.reflect.Field
import org.apache.commons.lang3.builder.ReflectionToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

class PIIMaskingToStringBuilder(value: Any?, toStringStyle: ToStringStyle = RecursivePIIMaskingToStringStyle) :
    ReflectionToStringBuilder(value, toStringStyle) {

    override fun getValue(field: Field?): Any? {
        return field?.getAnnotation(PII::class.java)?.let {
            mask(super.getValue(field))
        } ?: super.getValue(field)
    }

    companion object {
        fun toString(value: Any?): String = PIIMaskingToStringBuilder(value, RecursivePIIMaskingToStringStyle).toString()
        fun mask(value: Any?) = Configuration.dataMaskingFunction.mask(value)
    }
}