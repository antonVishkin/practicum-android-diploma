package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.NumberFormat
import java.util.Locale

object SalaryFormat {
    fun formatSalary(context: Context, salaryFrom: Int?, salaryTo: Int?, currency: String?): String {
        val fromText = context.getString(R.string.salary_from)
        val toText = context.getString(R.string.salary_to)
        val currencySymbol = getCurrencySymbol(currency.toString())
        val numberFormat: NumberFormat = NumberFormat.getInstance(Locale("ru", "RU"))

        return when {
            salaryFrom != null && salaryTo != null -> {
                "$fromText ${numberFormat.format(salaryFrom)} $toText ${numberFormat.format(salaryTo)} $currencySymbol"
            }

            salaryFrom != null -> {
                "$fromText ${numberFormat.format(salaryFrom)} $currencySymbol"
            }

            salaryTo != null -> {
                "$toText ${numberFormat.format(salaryTo)} $currencySymbol"
            }

            else -> context.getString(R.string.salary_not_specified)
        }
    }

    private fun getCurrencySymbol(currencyCode: String): String {
        return when (currencyCode) {
            "RUR", "RUB" -> "₽"
            "BYR" -> "Br"
            "USD" -> "$"
            "EUR" -> "€"
            "KZT" -> "₸"
            "UAH" -> "₴"
            "AZN" -> "₼"
            "UZS" -> "сўм"
            "GEL" -> "₾"
            "KGT" -> "сом"
            else -> {
                return currencyCode ?: ""
            }
        }
    }
}
