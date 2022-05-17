package net.anxdre.poskodigital.view.peternakan

import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItem

interface PeternakanView {
    fun showData(dataSource: PeternakanItem.X0)
    fun detachData()
    fun showLoading()
    fun hideLoading()
    fun showAddData()
    fun hideAddData()
    fun showViewAddData(dataSet: PeternakanItem.X0?)
    fun showOption(dataSource: PeternakanItem.X0)
    fun showError(msg: String)
    fun hideError()
}