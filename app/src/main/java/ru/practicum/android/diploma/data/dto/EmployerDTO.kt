package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class EmployerDTO(
    val id: String?,
    val name: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoDTO?,
)
