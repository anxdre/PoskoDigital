package net.anxdre.poskodigital.view.pertenakaninput

import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItem

interface PeternakanInputView {
    fun showLoading()
    fun hideLoading()
    fun showData(dataSet: PeternakanItem.X0)
    fun backNav()
    fun showError(msg: String)
}