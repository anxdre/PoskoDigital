package net.anxdre.poskodigital.view.perkebunan

import net.anxdre.poskodigital.data.api.model.perkebunan.PerkebunanGula

interface PerkebunanView {
    fun showData(dataSource: PerkebunanGula)
    fun detachData()
    fun showSubData(total: String?, consumption: String?, devisit: String?)
    fun showLoading()
    fun hideLoading()
    fun showAddData()
    fun showViewAddData(dataSet: PerkebunanGula?)
    fun showOption(dataSet: PerkebunanGula?)
    fun hideAddData()
    fun showError(msg: String)
    fun hideError()
}