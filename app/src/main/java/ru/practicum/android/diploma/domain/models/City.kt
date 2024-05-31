package ru.practicum.android.diploma.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: String,
    val name: String,
) : Parcelable
/*data class Region(
    val id: String,
    val name: String,
    val parentId: String,
)*/
