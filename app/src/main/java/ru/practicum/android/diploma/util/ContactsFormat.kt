//package ru.practicum.android.diploma.util
//
//import ru.practicum.android.diploma.domain.models.Phone
//import java.util.Locale
//
//object ContactsFormat {
//    private const val CITY_CODE_LENGTH = 3
//    private const val CITY_NUMBER_PARTS = 2
//    fun numberFormat(phone: String): String {
//        return String.format(
//            Locale.UK,
//            "+%1s (%2s) %3s-%4s-%5s",
//            phone.country,
//            phone.city,
//            phone.number?.take(CITY_CODE_LENGTH),
//            phone.number?.dropLast(CITY_NUMBER_PARTS)?.takeLast(CITY_NUMBER_PARTS),
//            phone.number?.takeLast(CITY_NUMBER_PARTS)
//        )
//    }
//}
