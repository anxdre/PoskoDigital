package net.anxdre.poskodigital.view.pertanianinput

import android.accounts.NetworkErrorException
import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanianInput
import net.anxdre.poskodigital.data.api.remote.pertanian.PertanianRepository

class PertanianInputPresenter(
    private val mApi: PertanianRepository,
    private val mView: PertanianInputView
) {
    suspend fun saveData(
        inputData: ItemPertanianInput,
        idUser: String,
        namaUser: String,
        date: String
    ) {
        mView.showLoading()
        if (inputData.id != null && inputData.idData != null) {
            try {
                mApi.UpdateItemPertanian(
                    inputData.id,
                    inputData.idData,
                    inputData.idKomoditas,
                    inputData.sisaStok,
                    inputData.tanggal,
                    inputData.produksi,
                    inputData.konsumsi
                )
                mView.backNav()
            } catch (e: NetworkErrorException) {
                mView.showError("Data gagal disimpan, pastikan ponsel terhubung ke internet")
            }
            mView.hideLoading()
        } else {
            if (inputData.idData != null) {
                try {
                    saveItem(inputData.idData, inputData)
                    mView.backNav()
                } catch (e: Exception) {
                    mView.showError("Terjadi Kesalahan Silahkan Coba Lagi")
                }
            } else {
                try {
                    val response = mApi.postDataPertanian(idUser, namaUser, date).await()
                    saveItem(response, inputData)
                    mView.backNav()
                } catch (e: Exception) {
                    mView.showError("Terjadi Kesalahan Silahkan Coba Lagi")
                }
            }
        }
    }

    private suspend fun saveItem(idData: String, data: ItemPertanianInput) {
        try {
            mApi.postItemPertanian(
                idData,
                data.idKomoditas,
                data.sisaStok,
                data.tanggal,
                data.produksi,
                data.konsumsi
            ).await()
        } catch (e: NetworkErrorException) {
            throw e
        }
    }
}