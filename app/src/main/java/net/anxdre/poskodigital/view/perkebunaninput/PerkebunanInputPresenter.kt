package net.anxdre.poskodigital.view.perkebunaninput

import android.accounts.NetworkErrorException
import net.anxdre.poskodigital.data.api.model.perkebunan.GulaInput
import net.anxdre.poskodigital.data.api.remote.perkebunan.PerkebunanGulaRepository

class PerkebunanInputPresenter(
    private val mView: PerkebunanInputView,
    private val mApi: PerkebunanGulaRepository
) {
    suspend fun saveData(
        inputData: ArrayList<GulaInput>,
        idUser: String,
        consumption: String,
        date: String
    ) {
        mView.showLoading()
        if (inputData[0].idPemakaian == null) {
            try {
                val response = mApi.postPemakaianGulaByDateAsync(idUser, consumption, date).await()
                if (response != "fail") {
                    for (i in inputData.indices) {
                        saveItem(response, inputData[i])
                    }
                    mView.backNav()
                } else throw NetworkErrorException()
            } catch (e: NetworkErrorException) {
                mView.showError("Data gagal disimpan, pastikan ponsel terhubung ke internet")
            }
        } else {
            try {
                inputData[0].idPemakaian?.let {
                    updatePemakaian(
                        it,
                        idUser,
                        consumption,
                        inputData[0].tanggal
                    )
                }
                for (i in inputData.indices) {
                    updateItem(
                        (inputData[0].id!!.toInt() + i).toString(),
                        inputData[0].idPemakaian!!,
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

    private suspend fun saveItem(idPemakaian: String, data: GulaInput) {
        try {
            mApi.postDataGulaByDateAsync(
                idPemakaian,
                data.idPabrik,
                data.jumlahPabrik,
                data.jumlahPetani,
                data.jumlahPedagang,
                data.tanggal
            ).await()
        } catch (e: NetworkErrorException) {
            throw e
        }
    }

    private suspend fun updateItem(id: String, idPemakaian: String, data: GulaInput) {
        try {
            mApi.updateDataGulaByDateAsync(
                id,
                idPemakaian,
                data.idPabrik,
                data.jumlahPabrik,
                data.jumlahPetani,
                data.jumlahPedagang,
                data.tanggal
            ).await()
        } catch (e: NetworkErrorException) {
            throw e
        }
    }

    private suspend fun updatePemakaian(
        id: String,
        idUser: String,
        consumption: String,
        date: String
    ) {
        try {
            mApi.updatePemakaianGulaByDateAsync(
                id,
                idUser,
                consumption,
                date
            ).await()
        } catch (e: NetworkErrorException) {
            throw e
        }
    }

}