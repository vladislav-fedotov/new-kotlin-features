package example

import kotlin.reflect.KFunction
import kotlin.reflect.jvm.ExperimentalReflectionOnLambdas
import kotlin.reflect.jvm.reflect

/**
 * Generation of lambda functions using invokedynamic reduces the binary sizes of applications compared to the
 * traditional anonymous class generation.
 */
fun main() {
    val lambda = {}
    println(lambda)
}