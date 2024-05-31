package ru.practicum.android.diploma.domain.sharing

class SharingInteractorImpl(
    private var externalNavigator: ExternalNavigator,
) : SharingInteractor {

    override fun shareApp(url: String) {
        externalNavigator.shareApp(url)
    }

    override fun phoneCall(phone: String) {
        externalNavigator.makeCall(phone)
    }

    override fun eMail(email: String) {
        externalNavigator.openEmail(email)
    }
}
