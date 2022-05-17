package net.anxdre.poskodigital.data.api.model.bulog

import com.google.gson.annotations.SerializedName


data class BulogTotal(
    @SerializedName("Beras")
    val beras: String?,
    @SerializedName("Gula Pasir")
    val gulaPasir: String?,
    @SerializedName("Jagung")
    val jagung: String?,
    @SerializedName("Minyak Goreng")
    val minyakGoreng: String?,
    @SerializedName("Tepung Terigu")
    val tepungTerigu: String?
)