package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Phone

private const val DROP_2 = 2
private const val DROP_3 = 3
private const val DROP_4 = 4
private const val DROP_5 = 5

object ContactsFormat {
    fun numberFormat(context: Context, phone: Phone): String {
        val text = context.getString(R.string.phone_format)
        return String.format(
            text,
            phone.country,
            phone.city,
            phone.number?.dropLast(DROP_4),
            phone.number?.drop(DROP_3)?.dropLast(DROP_2),
            phone.number?.drop(DROP_5)
        )
    }
}
