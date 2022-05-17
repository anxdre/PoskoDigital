package net.anxdre.poskodigital.data.api.remote.bulog

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.data.api.apiPostBasicRequestAsync
import net.anxdre.poskodigital.data.api.apiPostRequestStringAsync
import net.anxdre.poskodigital.data.api.model.bulog.BulogTotal
import net.anxdre.poskodigital.data.api.model.bulog.ItemBulog

class BulogResponse : BulogRepository {
    override fun getItemBulogByDate(tanggal: String, kancab: String): Deferred<ItemBulog> =
        apiPostBasicRequestAsync(
            "${BuildConfig.BASE_URL}item_bulog_get_by_kancab_date",
            ItemBulog::class.java,
            Pair("id_kancab", kancab),
            Pair("tanggal", tanggal)
        ) as Deferred<ItemBulog>

    override fun getTotalJatimByDate(tanggal: String): Deferred<BulogTotal> =
        apiPostBasicRequestAsync(
            "${BuildConfig.BASE_URL}jatim_bulog",
            BulogTotal::class.java,
            Pair("tanggal", tanggal),
            Pair("", "")
        ) as Deferred<BulogTotal>

    override fun postDataBulogAsync(
        idUsers: String,
        idKanCab: String,
        namaUser: String,
        tanggal: String
    ): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}post_data_bulog",
            Pair("id_users", idUsers),
            Pair("id_kancab", idKanCab),
            Pair("nama_users", namaUser),
            Pair("tanggal", tanggal),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " ")
        ) as Deferred<String>

    override fun postItemBulogAsync(
        idData: String,
        idKomoditas: String,
        idKanCab: String,
        stok: String,
        tanggal: String
    ): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}post_item_bulog",
            Pair("id_data", idData),
            Pair("id_komoditas", idKomoditas),
            Pair("id_kancab", idKanCab),
            Pair("stok", stok),
            Pair("tanggal", tanggal),
            Pair(" ", " "),
            Pair(" ", " ")
        ) as Deferred<String>

    override fun updateDataBulogAsync(
        id: String,
        idKanCab: String,
        tanggal: String
    ): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}put_data_bulog",
            Pair("id", id),
            Pair("id_kancab", idKanCab),
            Pair("tanggal", tanggal),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " ")
        ) as Deferred<String>

    override fun updateItemBulogAsync(
        id: String,
        idData: String,
        idKomoditas: String,
        idKanCab: String,
        stok: String,
        tanggal: String
    ): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}put_item_bulog",
            Pair("id", id),
            Pair("id_data", idData),
            Pair("id_komoditas", idKomoditas),
            Pair("id_kancab", idKanCab),
            Pair("stok", stok),
            Pair("tanggal", tanggal),
            Pair(" ", " ")
        ) as Deferred<String>

    override fun deleteDataBulog(idData: String): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}delete_data_bulog",
            Pair("id", idData),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " "),
            Pair(" ", " ")
        ) as Deferred<String>

}