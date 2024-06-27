package example


/**
 * [KT-67404](https://youtrack.jetbrains.com/issue/KT-67404)
 * Change default visibility of generated 'copy' method to match primary constructor visibility.
 * When the primary constructor of data classes is private, copy() probably should be private too?
 */


/**
 * Empty name is not allowed for Admin.
 */
data class Admin private constructor(val name: String, val id: Int) {
    companion object {
        fun of(name: String, id: Int): Admin {
            return Admin(if (name.isEmpty()) "Unknown" else name, id)
        }
    }
}

fun getAdmin(): Admin {
    // returns admin with empty name, which by default is impossible.
    return Admin.of("Alex", 1)//.copy(name = "", id = 2)
}