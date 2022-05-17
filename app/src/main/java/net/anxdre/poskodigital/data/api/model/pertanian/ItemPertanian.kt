package net.anxdre.poskodigital.data.api.model.pertanian

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemPertanian(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("0")
    val x0: List<X0?>?
) : Parcelable {
    @Parcelize
    data class X0(
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("defisit")
        val defisit: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("id_data")
        val idData: String?,
        @SerializedName("id_komoditas")
        val idKomoditas: String?,
        @SerializedName("konsumsi")
        val konsumsi: String?,
        @SerializedName("nama_komoditas")
        val namaKomoditas: String?,
        @SerializedName("produksi")
        val produksi: String?,
        @SerializedName("sisa_stok")
        val sisaStok: String?,
        @SerializedName("tahun_bulan")
        val tahunBulan: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    ) : Parcelable
}