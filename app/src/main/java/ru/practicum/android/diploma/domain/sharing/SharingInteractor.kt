package ru.practicum.android.diploma.domain.sharing

interface SharingInteractor {
    fun shareApp(vacancyId: String)
    fun getShareLink(vacancyId: String)
    fun phoneCall()
    fun eMail()
}
