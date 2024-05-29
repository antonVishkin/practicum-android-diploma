package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaDTO(
    val id: String,
    val parentId: String?,
    val name: String,
    val url: String,
    val areas: List<AreaDTO>
)
