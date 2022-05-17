package net.anxdre.poskodigital.view.disperindagadmin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.datetime.datePicker
import com.afollestad.materialdialogs.list.listItems
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_disperindag_admin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.adapter.DataDetailPerindagAdapter
import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindag
import net.anxdre.poskodigital.data.api.model.user.Kota
import net.anxdre.poskodigital.data.api.remote.disperindag.PerindagResponse
import net.anxdre.poskodigital.utils.OfflineJson
import net.anxdre.poskodigital.utils.SpFactory
import java.text.SimpleDateFormat
import java.util.*

class DisperindagAdmin : Fragment(), DisperindagAdminView {
    private val mApi by lazy { PerindagResponse() }
    private val mPresenter by lazy { DisperindagAdminPresenter(mApi, this) }

    @SuppressLint("SimpleDateFormat")
    private val df = SimpleDateFormat("yyyy-MM-dd")
    private var mIdAuthCity: String? = null
    private var dateFilter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mIdAuthCity = SpFactory(requireContext()).getAuthCity()
        if (dateFilter == null) {
            dateFilter = df.format(Calendar.getInstance().time)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_disperindag_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_filter_perindag_admin.text = dateFilter
        if (mIdAuthCity == "0") {
            initializeData()
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                mPresenter.reqData(mIdAuthCity!!, dateFilter!!)
            }
        }

        btn_add_data_perindag_admin.setOnClickListener {
            showInputData(null, mIdAuthCity!!, dateFilter!!, null, null)
        }

        sp_perindag_admin.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Main) { mPresenter.reqData(mIdAuthCity!!, dateFilter!!) }
        }

        btn_filter_perindag_admin.setOnClickListener {
            showFilter()
        }

        sp_kota_admin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Not Implemented
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mIdAuthCity = (position + 1).toString()
                rv_admin_item.adapter = null
                GlobalScope.launch(Dispatchers.Main) {
                    mPresenter.reqData(mIdAuthCity!!, dateFilter!!)
                }
            }

        }


    }

    override fun showInputData(
        id: String?,
        city: String,
        date: String,
        idSet: String?,
        dataSet: DataDetailPerindag?
    ) {
        findNavController()
            .navigate(
                R.id.action_nav_disperindag_admin_to_nav_disperindag_input, bundleOf(
                    Pair("city", city),
                    Pair("id", id),
                    Pair("date", date),
                    Pair("idSet", idSet),
                    Pair("dataSet", dataSet)
                )
            )
    }

    override fun showData(dataSource: DataDetailPerindag) {
        with(rv_admin_item) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = (DataDetailPerindagAdapter(dataSource) {
                MaterialDialog(requireContext()).show {
                    title(text = "Pilihan Aksi")
                    val myItems =
                        listOf("Edit Data Tanggal Ini", "Hapus Semua Data Pada Tanggal Ini")
                    listItems(items = myItems) { dialog, index, text ->
                        if (index != 0) {
                            it.idData?.let { it1 -> confirmDeleteData(it1) }
                        } else {
                            showInputData(
                                it.idData.toString(),
                                mIdAuthCity!!,
                                dateFilter!!,
                                it.id,
                                dataSource
                            )
                        }
                    }
                }
            })
        }
    }

    override fun showFilter() {
        MaterialDialog(requireContext()).show {
            datePicker { dialog, date ->
                dateFilter = df.format(date.time)
            }
        }.onDismiss {
            tv_filter_perindag_admin.text = dateFilter
            rv_admin_item.adapter = null
            GlobalScope.launch(Dispatchers.Main) {
                mPresenter.reqData(mIdAuthCity!!, dateFilter!!)
            }
        }
    }

    override fun showLoading() {
        sp_perindag_admin.isRefreshing = true
    }

    override fun hideLoading() {
        sp_perindag_admin.isRefreshing = false
    }

    override fun showError(msg: String) {
        tv_admin_perindag_desc.text = msg
        tv_admin_perindag_desc.visibility = View.VISIBLE
    }

    override fun showAddButton() {
        btn_add_data_perindag_admin.visibility = View.VISIBLE
    }

    override fun hideAddButton() {
        btn_add_data_perindag_admin.visibility = View.GONE
    }

    override fun hideError() {
        tv_admin_perindag_desc.visibility = View.GONE
    }

    override fun confirmDeleteData(idData: String) {
        MaterialDialog(requireContext()).show {
            title(text = "Apakah Anda Yakin Ingin Menghapus Data Pada Tanggal Ini ?")
            message(text = "Data yang telah dihapus tidak dapat dipulihkan kembali")
            positiveButton(text = "Hapus Data") {
                GlobalScope.launch(Dispatchers.Main) { mPresenter.deleteData(idData) }
            }
            negativeButton(text = "Kembali")
        }
    }

    override fun detachRecyclerview() {
        rv_admin_item.adapter = null
    }

    override fun initializeData() {
        val listKotaJson = Gson().fromJson(OfflineJson.kota, Kota::class.java)
        val listKota = arrayListOf<String>()

        for (i in listKotaJson.indices) {
            listKota.add("${listKotaJson[i].namaKota} (id : ${listKotaJson[i].id})")
        }

        sp_kota_admin.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            listKota
        )
        mIdAuthCity = (sp_kota_admin.selectedItemPosition + 1).toString()
        sp_kota_admin.visibility = View.VISIBLE
    }
}
