package net.anxdre.poskodigital.data.api.remote.pertanian

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.data.api.apiPostBasicRequestAsync
import net.anxdre.poskodigital.data.api.apiPostRequestStringAsync
import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanian

class PertanianResponse : PertanianRepository {
    override fun getItemPertanianByDate(tanggal: String): Deferred<ItemPertanian> =
        apiPostBasicRequestAsync(
            "${BuildConfig.BASE_URL}item_pertanian_get_by_date",
            ItemPertanian::class.java,
            Pair("tanggal", tanggal),
            Pair(" ", " ")
        ) as Deferred<ItemPertanian>


    override fun postDataPertanian(idUsers: String, namaUser: String, tanggal: String) =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}post_data_pertanian",
            Pair("id_users", idUsers),
            Pair("nama_users", namaUser),
            Pair("tanggal", tanggal),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " ")
        ) as Deferred<String>

    override fun postItemPertanian(
        idData: String,
        idKomoditas: String,
        sisaStok: String,
        tanggal: String,
        produksi: String,
        konsumsi: String
    ) = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}post_item_pertanian",
        Pair("id_data", idData),
        Pair("id_komoditas", idKomoditas),
        Pair("sisa_stok", sisaStok),
        Pair("tanggal", tanggal),
        Pair("produksi", produksi),
        Pair("konsumsi", konsumsi),
        Pair(" ", " ")
    ) as Deferred<String>

    override fun UpdateDataPertanian(
        id: String,
        idUsers: String,
        namaUser: String,
        tanggal: String
    ) = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}put_data_pertanian",
        Pair("id", id),
        Pair("id_users", idUsers),
        Pair("nama_users", namaUser),
        Pair("tanggal", tanggal),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " ")
    ) as Deferred<String>

    override fun UpdateItemPertanian(
        id: String,
        idData: String,
        idKomoditas: String,
        sisaStok: String,
        tanggal: String,
        produksi: String,
        konsumsi: String
    ) = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}put_item_pertanian",
        Pair("id", id),
        Pair("id_data", idData),
        Pair("id_komoditas", idKomoditas),
        Pair("sisa_stok", sisaStok),
        Pair("tanggal", tanggal),
        Pair("produksi", produksi),
        Pair("konsumsi", konsumsi)
    ) as Deferred<String>

    override fun deleteDataPertanian(idData: String) = apiPostRequestStringAsync(
        "${BuildConfig.BASE_URL}delete_data_pertanian",
        Pair("id", idData),
        Pair(" ", idData),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " "),
        Pair(" ", " ")
    ) as Deferred<String>
}