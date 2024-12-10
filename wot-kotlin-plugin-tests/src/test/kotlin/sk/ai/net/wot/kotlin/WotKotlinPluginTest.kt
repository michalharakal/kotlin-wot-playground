package sk.ai.net.wot.kotlin

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactly
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEmpty
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.tschuchort.compiletesting.JvmCompilationResult
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import java.lang.reflect.Modifier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

@OptIn(ExperimentalCompilerApi::class)
class WotKotlinPluginTest {
    @Test
    fun functionParameters() {
        val result = compile(
            sourceFile = SourceFile.kotlin(
                "AddressTest.kt",
                """
                import sk.ai.net.wot.kotlin.*

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
}
        """,
            ),
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode, result.messages)

        val testClass = result.classLoader.loadClass("CoffeeTest")

        // Burst doesn't make the class non-final as it has no reason to.
        assertThat(Modifier.isFinal(testClass.modifiers)).isTrue()

        val adapterInstance = testClass.constructors.single().newInstance()
        val log = testClass.getMethod("getLog").invoke(adapterInstance) as MutableList<*>

        // Burst drops @Test from the original test function.
        val originalTest = testClass.methods.single { it.name == "test" && it.parameterCount == 2 }
        assertThat(originalTest.isAnnotationPresent(Test::class.java)).isFalse()

        // Burst adds a specialization for each combination of parameters.
        val sampleSpecialization = testClass.getMethod("test_Regular_Milk")
        assertThat(sampleSpecialization.isAnnotationPresent(Test::class.java)).isTrue()
        sampleSpecialization.invoke(adapterInstance)
        assertThat(log).containsExactly("running Regular Milk")
        log.clear()

        // Burst doesn't add a no-parameter function because there's no default specialization.
        assertFailsWith<NoSuchMethodException> {
            testClass.getMethod("test")
        }
    }
}
