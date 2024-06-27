package example

class Phone {
    fun ring() {
        println("Ringing...")
    }
}


/**
 * In Kotlin 1.x, this will produce an error because local variables don’t carry any data flow information
 * and don’t contribute to the smart cast logic, preventing us from invoking the ring method on the device object.
 */

fun makeNoise(device: Any) {
    val isPhone = device is Phone
    if (isPhone) {
        device.ring()
    }
}

class Card(val holderName: String?)

/**
 *  Two smart casts: the card object is smart-casted to Card, and holderName is smart-casted from String? to String.
 */
fun foo(card: Any): String {
    val hasHolderName = card is Card && !card.holderName.isNullOrBlank()
    return when {
        hasHolderName -> card.holderName
        else -> "Unknown"
    }
}