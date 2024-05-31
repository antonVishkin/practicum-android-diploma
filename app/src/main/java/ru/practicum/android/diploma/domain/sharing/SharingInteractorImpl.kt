package ru.practicum.android.diploma.domain.sharing

import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.util.ContactsFormat

class SharingInteractorImpl(
    private var externalNavigator: ExternalNavigator,
) : SharingInteractor {

    override fun shareApp(url: String) {
        externalNavigator.shareApp(url)
    }

    override fun phoneCall(phone: Phone) {
        val formatNumber = ContactsFormat.numberFormat(phone)
        externalNavigator.makeCall(formatNumber)
    }

    override fun eMail(email: String) {
        externalNavigator.openEmail(email)
    }
}
