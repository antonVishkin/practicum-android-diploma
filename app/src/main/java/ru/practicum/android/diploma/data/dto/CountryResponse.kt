package ru.practicum.android.diploma.data.dto

import AreaDTO

data class CountryResponse(
    val countries: List<AreaDTO>
) : Response()
