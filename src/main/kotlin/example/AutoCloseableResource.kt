package example

import arrow.atomic.AtomicBoolean
import arrow.autoCloseScope

private class Resource : AutoCloseable {
    private val isActive = AtomicBoolean(true)

    fun isActive(): Boolean = isActive.get()

    fun shutdown() {
        require(isActive.compareAndSet(expected = true, new = false)) {
            "Already shut down"
        }
    }

    override fun close() {
        println("Closing resource...")
        shutdown()
    }
}

fun main() {
    Resource().use { resource ->
        println("Is resource active: ${resource.isActive()}")
        Resource().use { resource2 ->
            println("Is resource_2 active: ${resource2.isActive()}")
        }
    }

    autoCloseScope {
        Resource().use { resource ->
            println("Is resource_1 active: ${resource.isActive()}")
        }
        Resource().use { resource ->
            println("Is resource_2 active: ${resource.isActive()}")
        }
    }
}