package net.anxdre.poskodigital.data.api.remote.disperindag

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindag
import net.anxdre.poskodigital.data.api.model.disperindag.DataPerindag

interface PerindagRepository {
    fun getHightestValueAsync(idKomoditas: String, date: String): Deferred<DataDetailPerindag>
    fun getLowestValueAsync(idKomoditas: String, date: String): Deferred<DataDetailPerindag>
    fun getDataPerindagByKotaAsync(idKota: String, date: String): Deferred<DataPerindag>
    fun getDataDetailPerindagByDataAsync(idData: String): Deferred<DataDetailPerindag>
    fun deleteDataAsync(idData: String): Deferred<String>
    fun saveDataAsync(idUser: String, idCity: String, date: String): Deferred<String>
    fun saveItemAsync(
        idData: String,
        idCity: String,
        idItem: String,
        price: String,
        date: String
    ): Deferred<String>

    fun updateItemAsync(
        idDataSet: String,
        idData: String,
        idCity: String,
        idItem: String,
        price: String,
        date: String
    ): Deferred<String>
}