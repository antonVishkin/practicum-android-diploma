import com.google.gson.annotations.SerializedName

data class AreaDTO(
    val id: String,
    @SerializedName("parentId")
    val parentId: String?,
    val name: String,
    val url: String?,
    val areas: List<AreaDTO>
) {

   /* fun getAreasList(): List<AreaDTO> {
        val countries = mutableListOf<AreaDTO>()

        for (area in areas) {
            if (area.parentId == null) {
                countries.add(area)
            }
        }
        return countries
    }

    fun getRegionsList(): List<AreaDTO> {
        val regions = mutableListOf<AreaDTO>()

        for (area in areas) {
            if (area.parentId != null) {
                regions.add(area)
            }
        }
        return regions
    }*/
}
