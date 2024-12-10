package sk.ai.net.wot

sealed class DataSchemaValue {
    object NullValue : DataSchemaValue()
    data class BooleanValue(val value: Boolean) : DataSchemaValue()
    data class NumberValue(val value: Number) : DataSchemaValue()
    data class StringValue(val value: String) : DataSchemaValue()
    data class ObjectValue(val value: Map<String, DataSchemaValue>) : DataSchemaValue()
    data class ArrayValue(val value: List<DataSchemaValue>) : DataSchemaValue()
}

class DataSchemaBuilder {

    private var currentValue: DataSchemaValue = DataSchemaValue.NullValue

    fun boolean(value: Boolean) = apply {
        currentValue = DataSchemaValue.BooleanValue(value)
    }

    fun number(value: Number) = apply {
        currentValue = DataSchemaValue.NumberValue(value)
    }

    fun string(value: String) = apply {
        currentValue = DataSchemaValue.StringValue(value)
    }

    fun obj(init: ObjectBuilder.() -> Unit) = apply {
        val builder = ObjectBuilder().apply(init)
        currentValue = DataSchemaValue.ObjectValue(builder.build())
    }

    fun array(init: ArrayBuilder.() -> Unit) = apply {
        val builder = ArrayBuilder().apply(init)
        currentValue = DataSchemaValue.ArrayValue(builder.build())
    }

    fun build(): DataSchemaValue = currentValue
}

class ObjectBuilder {
    private val map = mutableMapOf<String, DataSchemaValue>()

    fun field(name: String, init: DataSchemaBuilder.() -> Unit) {
        val builder = DataSchemaBuilder().apply(init)
        map[name] = builder.build()
    }

    fun build(): Map<String, DataSchemaValue> = map
}

class ArrayBuilder {
    private val list = mutableListOf<DataSchemaValue>()

    fun item(init: DataSchemaBuilder.() -> Unit) {
        val builder = DataSchemaBuilder().apply(init)
        list.add(builder.build())
    }

    fun build(): List<DataSchemaValue> = list
}