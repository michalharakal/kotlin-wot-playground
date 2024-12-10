package wot

import sk.ai.net.wot.*

fun main() {
    val schema = DataSchemaBuilder().obj {
        field("name") { string("John") }
        field("age") { number(30) }
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
}