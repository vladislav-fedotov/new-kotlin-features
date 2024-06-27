package example

import example.Failure.InvalidUserDetails.USER_PARTICIPANT_ALREADY_EXISTS
import example.Failure.InvalidUserDetails.USER_NAME_ALREADY_EXISTS
import example.Failure.InvalidUserDetails.USER_NOT_FOUND
import example.Failure.InvalidUserDetails.USER_ID_ALREADY_EXISTS
import example.Failure.InvalidUserFailure

/**
 * # Guards
 *
 * **Type**: Design proposal
 * **Author**: Alejandro Serrano
 * **Contributors**: Mikhail Zarechenskii
 * **Discussion**: [KEEP-371](https://github.com/Kotlin/KEEP/issues/371)
 * **Status**: Experimental expected for 2.1
 * **Related YouTrack issue**: [KT-13626](https://youtrack.jetbrains.com/issue/KT-13626)
 */

sealed interface Failure {
    enum class InvalidUserDetails {
        USER_NAME_ALREADY_EXISTS,
        USER_ID_ALREADY_EXISTS,
        USER_NOT_FOUND,
        USER_PARTICIPANT_ALREADY_EXISTS,
    }
    data class InvalidUserFailure(val details: InvalidUserDetails) : Failure
}

fun render(someFailure: Failure): String = when (val failure = someFailure) {
    is InvalidUserFailure if failure.details == USER_PARTICIPANT_ALREADY_EXISTS -> "User participant already exists"
    is InvalidUserFailure if failure.details == USER_NAME_ALREADY_EXISTS -> "User name already exists"
    is InvalidUserFailure -> "User data invalid"
    else -> "error"
}

fun main() {
    InvalidUserFailure(USER_PARTICIPANT_ALREADY_EXISTS).let(::render).also(::println)
    InvalidUserFailure(USER_NAME_ALREADY_EXISTS).let(::render).also(::println)
    InvalidUserFailure(USER_NOT_FOUND).let(::render).also(::println)
    InvalidUserFailure(USER_ID_ALREADY_EXISTS).let(::render).also(::println)
}