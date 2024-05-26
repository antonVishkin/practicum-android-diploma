package ru.practicum.android.diploma.data.dto

data class SearchResponse(val items: ArrayList<VacancyDTO>?, val page: Int, val pages: Int, val found: Int) : Response()
