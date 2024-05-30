package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Phone

private const val DROP_2 = 2
private const val DROP_3 = 3
private const val DROP_4 = 4
private const val DROP_5 = 5

object ContactsFormat {
    fun numberFormat(phone: Phone): String {
        return String.format(
            "+%s (%s) %s-%s-%s",
            phone.country,
            phone.city,
            phone.number?.dropLast(DROP_4),
            phone.number?.drop(DROP_3)?.dropLast(DROP_2),
            phone.number?.drop(DROP_5)
        )
    }
}
