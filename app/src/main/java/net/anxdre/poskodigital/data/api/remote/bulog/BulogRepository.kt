package net.anxdre.poskodigital.data.api.remote.bulog

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.data.api.model.bulog.BulogTotal
import net.anxdre.poskodigital.data.api.model.bulog.ItemBulog

interface BulogRepository {
    fun getItemBulogByDate(tanggal: String, idKanCab: String): Deferred<ItemBulog>
    fun getTotalJatimByDate(tanggal: String): Deferred<BulogTotal
            >

    fun postDataBulogAsync(
        idUsers: String,
        idKanCab: String,
        namaUser: String,
        tanggal: String
    ): Deferred<String>

    fun postItemBulogAsync(
        idData: String,
        idKomoditas: String,
        idKanCab: String,
        stok: String,
        tanggal: String
    ): Deferred<String>

    fun updateDataBulogAsync(id: String, idKanCab: String, tanggal: String): Deferred<String>
    fun updateItemBulogAsync(
        id: String,
        idData: String,
        idKomoditas: String,
        idKanCab: String,
        stok: String,
        tanggal: String
    ): Deferred<String>

    fun deleteDataBulog(idData: String): Deferred<String>
}