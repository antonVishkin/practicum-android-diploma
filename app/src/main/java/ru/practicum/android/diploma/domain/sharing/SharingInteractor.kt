package ru.practicum.android.diploma.domain.sharing

interface SharingInteractor {
    fun shareApp(url: String)
    fun phoneCall(phone: String)
    fun eMail(email: String)
}
