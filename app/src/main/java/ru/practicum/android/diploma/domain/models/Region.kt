package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Region(
    val id: String,
    val name: String,
    val parentId: String?,
    val cities: List<City>
) : Parcelable
/*data class Region(
    val id: String,
    val name: String,
    val parentId: String,
)*/
