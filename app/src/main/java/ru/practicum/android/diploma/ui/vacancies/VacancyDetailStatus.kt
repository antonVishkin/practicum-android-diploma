package ru.practicum.android.diploma.ui.vacancies

const val DEFAULT_NETWORK_RESPONSE = 0

sealed class VacancyDetailStatus<out T>(val code: Int = DEFAULT_NETWORK_RESPONSE, val data: T? = null) {
    class Default : VacancyDetailStatus<Nothing>()
    class Loading : VacancyDetailStatus<Nothing>()
    class EmptyContent : VacancyDetailStatus<Nothing>()
    class NoConnection() : VacancyDetailStatus<Nothing>()
    class Error(newResponseCode: Int = DEFAULT_NETWORK_RESPONSE, message: String = "Error") :
        VacancyDetailStatus<Nothing>(code = newResponseCode)
    class Content<T>(networkData: T) : VacancyDetailStatus<T>(data = networkData)
}
