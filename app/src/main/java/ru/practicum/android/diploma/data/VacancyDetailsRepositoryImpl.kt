package ru.practicum.android.diploma.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.DTOConverters
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsRepositoryImpl(
    private val client: NetworkClient,
    private val dTOConverters: DTOConverters,
    private val context: Context
) : VacancyDetailsRepository {
    override fun getVacancyDetails(vacancyId: String): Flow<Result<VacancyDetails>> = flow {
        val response = client.doRequest(VacancyDetailsRequest(vacancyId))
        when (response.resultCode) {
            CLIENT_SUCCESS_RESULT_CODE -> {
                val detailsResponse = response as VacancyDetailsResponse
                val vacancyDetails = dTOConverters.map(detailsResponse)
                emit(Result.success(vacancyDetails))
            }

            else -> {
                emit(Result.failure(Throwable(context.getString(R.string.details_vacancy_error))))
            }
        }
    }

    companion object {
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
