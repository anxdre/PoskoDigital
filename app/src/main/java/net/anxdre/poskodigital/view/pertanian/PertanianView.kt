package net.anxdre.poskodigital.view.pertanian

import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanian
import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanianInput

interface PertanianView {
    fun showData(dataSource: ItemPertanian)
    fun detachData()
    fun showLoading()
    fun hideLoading()
    fun showAddData()
    fun showIdData(idData: String)
    fun showViewAddData(dataSet: ItemPertanianInput?)
    fun showOption(dataSet: ItemPertanianInput?)
    fun showError(msg: String)
    fun hideError()
}