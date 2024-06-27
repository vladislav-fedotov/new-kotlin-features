package example

import kotlinx.datetime.Clock

fun interface Logger {
    fun log(message: String)
}

//fun Logger {
//    fun log(message: String)
//}

data class User(val id: Long, val name: String, val age: Int) {
    context(Logger)
    fun save() {
        logWithTime("start saving user $id")
        // do something
        logWithTime("end saving user $id")
    }

    context(Logger)
    private fun logWithTime(message: String) =
        log("[${Clock.System.now()}] $message")

}

fun main() {
    val logger = object : Logger {
        override fun log(message: String) {
            println(message)
        }
    }

    val user = User(1, "Alice", 25)

    with(logger) {
        user.save()
    }

    with(Logger { message -> println(message) }) {
        user.save()
    }

    withA {
        bar()
    }
}


class A
fun A.foo() = println("foo")
fun withA(block: A.() -> Unit) = block(A())

context(A)
fun bar() = foo()