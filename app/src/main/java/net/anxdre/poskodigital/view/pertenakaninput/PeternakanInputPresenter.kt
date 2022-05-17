package net.anxdre.poskodigital.view.pertenakaninput

import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItemInput
import net.anxdre.poskodigital.data.api.remote.peternakan.PeternakanRepository

class PeternakanInputPresenter(
    private val mApi: PeternakanRepository,
    private val mView: PeternakanInputView
) {
    suspend fun saveData(dataSet: PeternakanItemInput, tanggal: String) {
        mView.showLoading()
        if (dataSet.id == null) {
            try {
                mApi.postPeternakanData(dataSet).await()
                mView.backNav()
            } catch (e: Exception) {
                mView.hideLoading()
                mView.showError("Terjadi Kesalahan")
            }
        } else {
            try {
                mApi.updatePeternakanData(dataSet).await()
                mView.backNav()
            } catch (e: Exception) {
                mView.hideLoading()
                mView.showError("Terjadi Kesalahan")
            }
        }
        mView.hideLoading()
    }
}