package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.DTOConverters
import ru.practicum.android.diploma.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyDetailsRepositoryImpl(
    private val retrofitNetworkClient: RetrofitNetworkClient,
    private val dTOConverters: DTOConverters,
) : VacancyDetailsRepository {
    override fun getVacancyDetails(vacancyId: String): Flow<Result<VacancyDetails>> = flow {
        val response = retrofitNetworkClient.doRequest(VacancyDetailsRequest(vacancyId))
        when (response.resultCode) {
            CLIENT_SUCCESS_RESULT_CODE -> {
                val detailsResponse = response as VacancyDetailsResponse
                val vacancyDetails = dTOConverters.map(detailsResponse)
                emit(Result.success(vacancyDetails))
            }

            else -> {
                emit(Result.failure(Throwable("Failed to fetch vacancy details")))
            }
        }
    }

    companion object {
        const val CLIENT_SUCCESS_RESULT_CODE = 200
    }
}
