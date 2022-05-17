package net.anxdre.poskodigital.view.disperindaginput

import net.anxdre.poskodigital.data.api.model.user.KomuditasItem
import net.anxdre.poskodigital.data.api.remote.disperindag.PerindagRepository

class DisperindagInputPresenter(
    private val mApi: PerindagRepository,
    private val mView: DisperindagInputView
) {
    suspend fun saveData(
        dataSetHarga: ArrayList<String>,
        dataSetItem: ArrayList<KomuditasItem>,
        idUser: String,
        city: String,
        date: String,
        idData: String?,
        idSet: String?
    ) {
        mView.showLoading()
        if (idData.isNullOrBlank()) {
            try {
                val response = mApi.saveDataAsync(idUser, city, date).await()
                for (i in dataSetItem.indices) {
                    saveItem(
                        response,
                        city,
                        dataSetItem[i].id.toString(),
                        dataSetHarga[i],
                        date
                    )
                }
            } catch (e: Exception) {
                mView.showError("Terjadi Kesalahan Silahkan Coba Lagi")
            }
        } else {
            try {
                for (i in dataSetItem.indices) {
                    updateData(
                        (idSet!!.toInt() + i).toString(),
                        idData,
                        city,
                        dataSetItem[i].id.toString(),
                        dataSetHarga[i],
                        date
                    )
                }
            } catch (e: Exception) {
                mView.showError("Terjadi Kesalahan")
            }
        }
        mView.hideLoading()
        mView.backNav()
    }

    private suspend fun saveItem(
        idData: String,
        city: String,
        idItem: String,
        price: String,
        date: String
    ) {
        try {
            mApi.saveItemAsync(idData, city, idItem, price, date).await()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun updateData(
        idSet: String,
        idDataUp: String,
        city: String,
        idItem: String,
        price: String,
        date: String
    ) {
        try {
            mApi.updateItemAsync(idSet, idDataUp, city, idItem, price, date).await()
        } catch (e: Exception) {
            mView.showError("Gagal menyimpan data")
        }
    }

}