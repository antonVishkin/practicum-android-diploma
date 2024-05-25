package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.Industry

data class IndustryResponse(
    val id: String,
    val industries: List<IndustryDto>,
    val name: String
)

fun mapToListIndustries(industriesList: List<IndustryResponse>): List<Industry> {
    val list = mutableListOf<Industry>()
    industriesList.forEach {
        list.addAll(createIndustriesList(it.industries))
    }
    return list
}

fun createIndustriesList(industriesList: List<IndustryDto>): List<Industry> {
    val list = mutableListOf<Industry>()
    industriesList.forEach {
        list.add(Industry(id = it.id, name = it.name))
    }
    return list
}
