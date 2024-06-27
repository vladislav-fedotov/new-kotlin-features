package example._7_validation.ex2

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import arrow.core.right
import example._7_validation.ex2.GeneralError.CompanyAlreadyExist
import example._7_validation.ex2.GeneralError.EmptyCompanyName
import example._7_validation.ex2.GeneralError.InvalidProgrammingLanguage
import example._7_validation.ex2.ProgrammingLanguage.Companion.GO
import example._7_validation.ex2.ProgrammingLanguage.Companion.JAVA
import example._7_validation.ex2.ProgrammingLanguage.Companion.KOTLIN

sealed class GeneralError {
    object EmptyCompanyName : GeneralError()
    object CompanyAlreadyExist : GeneralError()
    object InvalidProgrammingLanguage : GeneralError()
}

data class Company(
    val name: String,
    val mainProgrammingLanguage: ProgrammingLanguage = KOTLIN
)

data class ProgrammingLanguage(val name: String) {
    companion object {
        val KOTLIN = ProgrammingLanguage("Kotlin")
        val JAVA = ProgrammingLanguage("Java")
        val GO = ProgrammingLanguage("Go")
    }
}

data class CompanyRepository(val companies: MutableList<Company>)

val companiesRepository = CompanyRepository(
    mutableListOf(
        Company("Arrow.kt", KOTLIN), Company("Wolt", GO), Company("OldBank", JAVA)
    )
)

fun buildCompanyName(kurzCompanyName: String?): Either<EmptyCompanyName, String> =
        kurzCompanyName?.let { (kurzCompanyName.first().uppercase() + kurzCompanyName.drop(1).lowercase()).right() } ?: EmptyCompanyName.left()


fun addCompany(companyName: String): Either<CompanyAlreadyExist, Company> {
    companiesRepository.companies.find { it.name == companyName }?.let { return CompanyAlreadyExist.left() }
    val newlyCreatedCompany = Company(companyName)
    companiesRepository.companies.add(newlyCreatedCompany)
    return newlyCreatedCompany.right()
}

fun chooseProgrammingLanguage(company: Company): Either<InvalidProgrammingLanguage, Unit> {
    if (company.name.endsWith(".kt") && company.mainProgrammingLanguage == JAVA) {
        companiesRepository.companies.remove(company)
        companiesRepository.companies.add(company.copy(mainProgrammingLanguage = KOTLIN))
    } else if (company.name.contains("o") && company.mainProgrammingLanguage == GO) {
        return InvalidProgrammingLanguage.left()
    }
    return Unit.right()
}

fun main() {
    either<GeneralError, Unit> {
        val companyName = buildCompanyName("wolt").bind()
        val company = addCompany(companyName).bind()
        chooseProgrammingLanguage(company).bind()
        println(companiesRepository)
    }
}