package ru.practicum.android.diploma.domain.sharing

class SharingInteractorImpl(
    private var externalNavigator: ExternalNavigator,
) : SharingInteractor {


    override fun shareApp(vacancyId: String) {
        externalNavigator.shareApp(vacancyId)
    }

    override fun phoneCall() {
        externalNavigator.makeCall(phoneNumber = String())
    }

    override fun eMail() {
        externalNavigator.openEmail()
    }

    override fun getShareLink(vacancyId: String) {

        externalNavigator.getShareLink(vacancyId)
    }
}
