package net.anxdre.poskodigital.view.buloginput

import android.accounts.NetworkErrorException
import net.anxdre.poskodigital.data.api.model.bulog.ItemBulogInput
import net.anxdre.poskodigital.data.api.remote.bulog.BulogRepository

class BulogInputPresenter(
    private val mApi: BulogRepository,
    private val mView: BulogInputView
) {
    suspend fun saveData(
        inputData: ArrayList<ItemBulogInput>,
        idUser: String,
        idKancab: String,
        namaUser: String,
        date: String
    ) {
        mView.showLoading()
        if (inputData[0].idData == null) {
            try {
                val response = mApi.postDataBulogAsync(idUser, idKancab, namaUser, date).await()
                for (i in inputData.indices) {
                    saveItem(response, inputData[i])
                }
                mView.backNav()
            } catch (e: Exception) {
                mView.showError("Terjadi Kesalhan Silahkan Coba Lagi")
            }
        } else {
            try {
                for (i in inputData.indices) {
                    updateItem(
                        inputData[i].id!!,
                        inputData[i].idData!!,
                        inputData[i]
                    )
                }
                mView.backNav()
            } catch (e: NetworkErrorException) {
                mView.showError("Data gagal disimpan, pastikan ponsel terhubung ke internet")
            }
        }
        mView.hideLoading()
    }

    private suspend fun saveItem(idData: String, data: ItemBulogInput) {
        try {
            mApi.postItemBulogAsync(
                idData,
                data.idKomoditas,
                data.idKancab,
                data.stok,
                data.tanggal
            ).await()
        } catch (e: NetworkErrorException) {
            throw e
        }
    }

    private suspend fun updateItem(id: String, idData: String, data: ItemBulogInput) {
        try {
            mApi.updateItemBulogAsync(
                id,
                idData,
                data.idKomoditas,
                data.idKancab,
                data.stok,
                data.tanggal
            ).await()
        } catch (e: NetworkErrorException) {
            throw e
        }
    }
}