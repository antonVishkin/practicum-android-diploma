package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Phone

class ContactsFormat {
    companion object {
        fun numberFormat(phone: Phone): String {
            return String.format(
                "+%s (%s) %s-%s-%s",
                phone.country,
                phone.city,
                phone.number?.dropLast(4),
                phone.number?.drop(3)?.dropLast(2),
                phone.number?.drop(5)
            )
        }
    }
}
