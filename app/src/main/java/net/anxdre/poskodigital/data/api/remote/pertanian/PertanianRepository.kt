package net.anxdre.poskodigital.data.api.remote.pertanian

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanian

interface PertanianRepository {
    fun getItemPertanianByDate(tanggal: String): Deferred<ItemPertanian>

    fun postDataPertanian(idUsers: String, namaUser: String, tanggal: String): Deferred<String>
    fun postItemPertanian(
        idData: String,
        idKomoditas: String,
        sisaStok: String,
        tanggal: String,
        produksi: String,
        konsumsi: String
    ): Deferred<String>

    fun UpdateDataPertanian(
        id: String,
        idUsers: String,
        namaUser: String,
        tanggal: String
    ): Deferred<String>

    fun UpdateItemPertanian(
        id: String,
        idData: String,
        idKomoditas: String,
        sisaStok: String,
        tanggal: String,
        produksi: String,
        konsumsi: String
    ): Deferred<String>

    fun deleteDataPertanian(idData: String): Deferred<String>
}