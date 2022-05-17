package net.anxdre.poskodigital.data.api.model.pertanian

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemPertanianInput(
    val id: String?, val idData: String?, val idKomoditas: String,
    val sisaStok: String, val tanggal: String, val produksi: String, val konsumsi: String
) : Parcelable