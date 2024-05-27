package ru.practicum.android.diploma.data.sharing

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator

class ExternalNavigatorImpl(
    private val context: Context,
) : ExternalNavigator {

    override fun shareApp(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
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

    override fun openEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}
