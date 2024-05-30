package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Phone

object ContactsFormat {
    fun numberFormat(phone: Phone): String {
        return String.format(
            "+%1s (%2s) %3s-%4s-%5s",
            phone.country,
            phone.city,
            phone.number?.dropLast(4),
            phone.number?.drop(3)?.dropLast(2),
            phone.number?.drop(5)
        )
    }
}
