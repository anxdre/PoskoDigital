package net.anxdre.poskodigital.view.pertanianinput

import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanianInput

interface PertanianInputView {
    fun showLoading()
    fun hideLoading()
    fun showData(dataSet: ItemPertanianInput)
    fun backNav()
    fun showError(msg: String)
}