package example._7_validation.ex3

import arrow.core.raise.Raise
import arrow.core.raise.ensureNotNull
import arrow.core.raise.recover
import example._7_validation.ex3.GeneralError.CompanyAlreadyExist
import example._7_validation.ex3.GeneralError.EmptyCompanyName
import example._7_validation.ex3.GeneralError.InvalidProgrammingLanguage
import example._7_validation.ex3.ProgrammingLanguage.Companion.GO
import example._7_validation.ex3.ProgrammingLanguage.Companion.JAVA
import example._7_validation.ex3.ProgrammingLanguage.Companion.KOTLIN

sealed class GeneralError {
    object EmptyCompanyName : GeneralError()
    object CompanyAlreadyExist : GeneralError()
    object InvalidProgrammingLanguage : GeneralError()
}

data class Company(val name: String, val mainProgrammingLanguage: ProgrammingLanguage = KOTLIN)

data class ProgrammingLanguage(val name: String) {
    companion object {
        val KOTLIN = ProgrammingLanguage("Kotlin")
        val JAVA = ProgrammingLanguage("Java")
        val GO = ProgrammingLanguage("Go")
    }
}

data class CompanyRepository(val companies: MutableList<Company>)

val companiesRepository = CompanyRepository(mutableListOf(Company("Arrow.kt", KOTLIN), Company("Wolt", GO), Company("OldBank", JAVA)))

context(Raise<EmptyCompanyName>)
fun buildCompanyName(kurzCompanyName: String?): String =
    ensureNotNull(kurzCompanyName) { EmptyCompanyName }.let { (kurzCompanyName.first().uppercase() + kurzCompanyName.drop(1).lowercase()) }

context(Raise<CompanyAlreadyExist>)
fun addCompany(companyName: String): Company {
    companiesRepository.companies.find { it.name == companyName }?.let { raise(CompanyAlreadyExist) }
    val newlyCreatedCompany = Company(companyName)
    companiesRepository.companies.add(newlyCreatedCompany)
    return newlyCreatedCompany
}

context(Raise<InvalidProgrammingLanguage>)
fun chooseProgrammingLanguage(company: Company) {
    if (company.name.endsWith(".kt") && company.mainProgrammingLanguage == JAVA) {
        companiesRepository.companies.remove(company)
        companiesRepository.companies.add(company.copy(mainProgrammingLanguage = KOTLIN))
    } else if (company.name.contains("o") && company.mainProgrammingLanguage == GO) {
        raise(InvalidProgrammingLanguage)
    }
}

context(Raise<InvalidProgrammingLanguage>, Raise<CompanyAlreadyExist>, Raise<EmptyCompanyName>)
fun createNewCompany(companyName: String) {
        val properCompanyName = buildCompanyName(companyName)
        val company = addCompany(properCompanyName)
        chooseProgrammingLanguage(company)
        println(companiesRepository)
}

fun main() {
    recover({
        createNewCompany("Wolt")
    }){ e ->
        when (e) {
            is EmptyCompanyName -> println("Company name cannot be empty")
            is CompanyAlreadyExist -> println("Company already exists")
            is InvalidProgrammingLanguage -> println("Invalid programming language")
            else -> println("Some error occurred")
        }
    }
}