package net.anxdre.poskodigital.data.api.model.perkebunan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PerkebunanGula(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("0")
    val x0: List<X0>?
) : Parcelable {
    @Parcelize
    data class X0(
        @SerializedName("bulan_tahun")
        val bulanTahun: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("id_pabrik")
        val idPabrik: String?,
        @SerializedName("id_pemakaian")
        val idPemakaian: String?,
        @SerializedName("jumlah_pabrik")
        val jumlahPabrik: String?,
        @SerializedName("jumlah_pedagang")
        val jumlahPedagang: String?,
        @SerializedName("jumlah_petani")
        val jumlahPetani: String?,
        @SerializedName("nama_pabrik")
        val namaPabrik: String?,
        @SerializedName("total")
        val total: String?,
        @SerializedName("updated_at")
        val updatedAt: String?
    ) : Parcelable
}