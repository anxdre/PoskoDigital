package net.anxdre.poskodigital.data.api.model.disperindag

import com.google.gson.annotations.SerializedName


data class DataPerindag(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("0")
    val x0: X0?
)

data class X0(
    @SerializedName("bulan")
    val bulan: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("id_kota")
    val idKota: String?,
    @SerializedName("id_users")
    val idUsers: String?,
    @SerializedName("nama_kota")
    val namaKota: String?,
    @SerializedName("tahun")
    val tahun: String?,
    @SerializedName("tanggal")
    val tanggal: String?,
    @SerializedName("updated_at")
    val updatedAt: Any?
)