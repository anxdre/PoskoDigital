package net.anxdre.poskodigital.view.bulog

import net.anxdre.poskodigital.data.api.model.bulog.ItemBulog

interface BulogView {
    fun showData(dataSource: ItemBulog)
    fun detachData()

    //    fun showSubData(total:String?,consumption:String?,devisit:String?)
    fun showLoading()
    fun hideLoading()
    fun showAddData()
    fun showViewAddData(dataSet: ItemBulog?)
    fun showOption(dataSet: ItemBulog?)
    fun hideAddData()
    fun showError(msg: String)
    fun hideError()
}