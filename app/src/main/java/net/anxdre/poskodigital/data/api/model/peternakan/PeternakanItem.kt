package net.anxdre.poskodigital.data.api.model.peternakan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PeternakanItem(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("0")
    val x0: X0?
) : Parcelable {
    @Parcelize
    data class X0(
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("id_users")
        val idUsers: String?,
        @SerializedName("kebutuhan_ayam")
        val kebutuhanAyam: String?,
        @SerializedName("kebutuhan_sapi")
        val kebutuhanSapi: String?,
        @SerializedName("kebutuhan_telur")
        val kebutuhanTelur: String?,
        @SerializedName("ketersediaan_ayam")
        val ketersediaanAyam: String?,
        @SerializedName("ketersediaan_sapi")
        val ketersediaanSapi: String?,
        @SerializedName("ketersediaan_telur")
        val ketersediaanTelur: String?,
        @SerializedName("nama_users")
        val namaUsers: String?,
        @SerializedName("stok_ayam")
        val stokAyam: String?,
        @SerializedName("stok_sapi")
        val stokSapi: String?,
        @SerializedName("stok_telur")
        val stokTelur: String?,
        @SerializedName("tahun_bulan")
        val tahunBulan: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    ) : Parcelable
}