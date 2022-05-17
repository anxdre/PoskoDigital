package net.anxdre.poskodigital.data.api.model.perkebunan

import com.google.gson.annotations.SerializedName

data class PemakaianGula(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("0")
    val x0: X0?
) {
    data class X0(
        @SerializedName("bulan_tahun")
        val bulanTahun: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("defisit")
        val defisit: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("id_users")
        val idUsers: String?,
        @SerializedName("konsumsi")
        val konsumsi: String?,
        @SerializedName("produksi")
        val produksi: String?,
        @SerializedName("updated_at")
        val updatedAt: Any?
    )
}