package net.anxdre.poskodigital.data.api.model.peternakan

import com.google.gson.annotations.SerializedName

data class PeternakanItemInput(
    @SerializedName("id")
    val id: String?,
    @SerializedName("id_users")
    val idUser: String,
    @SerializedName("nama_users")
    val namaUser: String,
    @SerializedName("ketersediaan_sapi")
    val sapi: String,
    @SerializedName("ketersediaan_ayam")
    val ayam: String,
    @SerializedName("ketersediaan_telur")
    val telur: String,
    @SerializedName("kebutuhan_sapi")
    val kebutuhanSapi: String,
    @SerializedName("kebutuhan_ayam")
    val kebutuhanAyam: String,
    @SerializedName("kebutuhan_telur")
    val kebutuhanTelur: String,
    @SerializedName("tanggal")
    val tanggal: String
)