package ru.practicum.android.diploma.data.dto

data class SearchResponse(val resultCount: Int, val items: ArrayList<VacancyDTO>) : Response()
