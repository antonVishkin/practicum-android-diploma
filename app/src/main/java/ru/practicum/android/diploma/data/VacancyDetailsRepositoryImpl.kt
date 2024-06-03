package ru.practicum.android.diploma.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.VacancyDtoConverter
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.vacancies.VacancyDetailStatus

class VacancyDetailsRepositoryImpl(
    private val client: NetworkClient,
    private val converter: VacancyDtoConverter,
    private val context: Context
) : VacancyDetailsRepository {

    override fun getVacancyDetails(vacancyId: String): Flow<VacancyDetailStatus<Vacancy>> = flow {
        val response = client.doRequest(VacancyDetailsRequest(vacancyId))
        when (response.resultCode) {
            CLIENT_SUCCESS_RESULT_CODE -> {
                val detailsResponse = response as VacancyDetailsResponse
                if (detailsResponse.vacancy != null) {
                    val vacancyDetails = converter.map(detailsResponse.vacancy)
                    emit(VacancyDetailStatus.Content(vacancyDetails))
                } else {
                    emit(
                        VacancyDetailStatus.Error(
                            response.resultCode,
                            context.getString(R.string.details_vacancy_error)
                        )
                    )
                }
            }

            NO_CONNECTION_HTTP_CODE -> {
                emit(VacancyDetailStatus.NoConnection())
            }

            SERVER_ERROR_HTTP_CODE -> {
                emit(VacancyDetailStatus.Error(response.resultCode, context.getString(R.string.details_vacancy_error)))
            }

            else -> {
                emit(VacancyDetailStatus.NoConnection())
            }
        }
    }

    companion object {
        const val CLIENT_SUCCESS_RESULT_CODE = 200
        const val NO_CONNECTION_HTTP_CODE = 500
        const val SERVER_ERROR_HTTP_CODE = 400
    }
}
