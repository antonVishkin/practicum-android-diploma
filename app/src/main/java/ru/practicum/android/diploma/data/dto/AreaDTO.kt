package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class AreaDTO(
    val id: String,
    val parentId: String?,
    val name: String,
    val areas: List<AreaDTO>
) {

    /* fun getAreasList(): List<ru.practicum.android.diploma.data.dto.AreaDTO> {
         val countries = mutableListOf<ru.practicum.android.diploma.data.dto.AreaDTO>()

         for (area in areas) {
             if (area.parentId == null) {
                 countries.add(area)
             }
         }
         return countries
     }

     fun getRegionsList(): List<ru.practicum.android.diploma.data.dto.AreaDTO> {
         val regions = mutableListOf<ru.practicum.android.diploma.data.dto.AreaDTO>()

         for (area in areas) {
             if (area.parentId != null) {
                 regions.add(area)
             }
         }
         return regions
     }*/
}
