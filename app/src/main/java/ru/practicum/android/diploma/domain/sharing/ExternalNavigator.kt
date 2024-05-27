package ru.practicum.android.diploma.domain.sharing

interface ExternalNavigator {
    fun shareApp(url: String)
    fun makeCall(phoneNumber: String)
    fun openEmail(email: String)
}
