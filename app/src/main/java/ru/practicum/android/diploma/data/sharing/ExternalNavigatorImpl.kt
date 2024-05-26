package ru.practicum.android.diploma.data.sharing

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.runBlocking
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator

class ExternalNavigatorImpl(
    private val context: Context,
    private val retrofitNetworkClient: RetrofitNetworkClient
) : ExternalNavigator {

    override fun shareApp(vacancyId: String) {
        val vacancyLink = getVacancyLink(vacancyId)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, vacancyLink)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val shareIntent = Intent.createChooser(intent, null).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(shareIntent)
    }

    override fun makeCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    override fun openEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    private fun getVacancyLink(vacancyId: String): String {
        return runBlocking {
            val response = retrofitNetworkClient.doRequest(VacancyDetailsRequest(vacancyId)) as VacancyDetailsResponse
            response.alternateUrl ?: getShareLink(vacancyId)
        }
    }

    override fun getShareLink(vacancyId: String): String {
        return "https://hh.ru/vacancy/$vacancyId"
    }
}
