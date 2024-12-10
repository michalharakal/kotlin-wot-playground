package wot

import sk.ai.net.wot.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


fun main() {
    val schema = DataSchemaBuilder().obj {
        field("name") { string("John") }
        field("age") { number(MyNumber(30.0)) }
        field("isStudent") { boolean(false) }
        field("contact") {
            obj {
                field("email") { string("john.doe@example.com") }
                field("phone") { string("123456789") }
            }
        }
        field("hobbies") {
            array {
                item { string("reading") }
                item { string("traveling") }
            }
        }
    }.build()

    println(schema)

    val json = Json.encodeToString(schema)
    println(json)
}