package example

data class Employee(
    val name: String,
    val lastName: String,
)

fun main() {
    Employee("Alice", "Smith").let { (surname, firstName) ->
        println("Name: $surname, Last Name: $firstName")
    }

    val employee = Employee("Alice", "Smith")
    val (a, b) = employee
}