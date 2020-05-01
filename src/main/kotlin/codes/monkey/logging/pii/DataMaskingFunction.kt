package codes.monkey.logging.pii

/*
* Good read https://www.iri.com/blog/data-protection/data-masking-function-use/
* */
interface DataMaskingFunction {
    fun mask(value: Any?) : String
}

class DefaultDataMaskingFunction(): DataMaskingFunction {

    private val replaceWith: String = Configuration.defaultMaskWith

    override fun mask(value: Any?) = replaceWith
}