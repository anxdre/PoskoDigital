package net.anxdre.poskodigital.data.api.model.bulog

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemBulog(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("0")
    val x0: List<X0?>?
) : Parcelable {
    @Parcelize
    data class X0(
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("id_data")
        val idData: String?,
        @SerializedName("id_komoditas")
        val idKomoditas: String?,
        @SerializedName("nama_komoditas")
        val namaKomoditas: String?,
        @SerializedName("nama_kancab")
        val namaKancab: String?,
        @SerializedName("stok")
        val stok: String?,
        @SerializedName("tahun_bulan")
        val tahunBulan: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    ) : Parcelable
}