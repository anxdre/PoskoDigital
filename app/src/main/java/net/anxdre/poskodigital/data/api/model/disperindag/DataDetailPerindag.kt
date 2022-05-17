package net.anxdre.poskodigital.data.api.model.disperindag

import com.google.gson.annotations.SerializedName


class DataDetailPerindag : ArrayList<DataDetailPerindagItem>()

data class DataDetailPerindagItem(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("harga")
    val harga: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("id_data")
    val idData: String?,
    @SerializedName("id_komoditas")
    val idKomoditas: String?,
    @SerializedName("id_kota")
    val idKota: String?,
    @SerializedName("nama_komoditas")
    val namaKomoditas: String?,
    @SerializedName("nama_kota")
    val namaKota: String?,
    @SerializedName("tanggal")
    val tanggal: String?,
    @SerializedName("updated_at")
    val updatedAt: Any?
)