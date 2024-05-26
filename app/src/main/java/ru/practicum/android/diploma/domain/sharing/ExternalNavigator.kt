package ru.practicum.android.diploma.domain.sharing

interface ExternalNavigator {
    fun shareApp(vacancyId: String)
    fun makeCall(phoneNumber: String)
    fun openEmail()
    fun getShareLink(vacancyId: String): String
}
