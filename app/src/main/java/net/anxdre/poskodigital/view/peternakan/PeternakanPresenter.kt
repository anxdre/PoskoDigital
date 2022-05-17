package net.anxdre.poskodigital.view.peternakan

import net.anxdre.poskodigital.data.api.remote.peternakan.PeternakanRepository

class PeternakanPresenter(
    private val mView: PeternakanView,
    private val mApi: PeternakanRepository
) {
    suspend fun getData(date: String) {
        mView.showLoading()
        try {
            val response = mApi.getPeternakanData(date).await().x0
            if (response != null) {
                mView.hideError()
                mView.hideAddData()
                mView.showData(response)
            }
        } catch (e: Exception) {
            mView.showError("Data Tidak Ditemukan")
            mView.showAddData()
        }
        mView.hideLoading()
    }

    fun deleteData(id: String) {
        try {
            mApi.deletePeternakanData(id)
            mView.detachData()
            mView.showAddData()
            mView.showError("Data Berhasil Dihapus")
        } catch (e: Exception) {
            mView.showError("Data Tidak Ditemukan")
        }
    }
}