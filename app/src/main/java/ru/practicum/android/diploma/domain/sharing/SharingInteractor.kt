package ru.practicum.android.diploma.domain.sharing

import ru.practicum.android.diploma.domain.models.Phone

interface SharingInteractor {
    fun shareApp(url: String)
    fun phoneCall(phone: Phone)
    fun eMail(email: String)
}
