package net.anxdre.poskodigital.view.bulog

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
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.list.listItems
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_bulog.*
import kotlinx.android.synthetic.main.layout_bulog_total_info.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.adapter.DataItemBulogAdapter
import net.anxdre.poskodigital.data.api.model.bulog.BulogTotal
import net.anxdre.poskodigital.data.api.model.bulog.ItemBulog
import net.anxdre.poskodigital.data.api.model.bulog.Kancab
import net.anxdre.poskodigital.data.api.remote.bulog.BulogResponse
import net.anxdre.poskodigital.utils.OfflineJson
import net.anxdre.poskodigital.utils.SpFactory
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class BulogFragment : Fragment(), BulogView {
    private val mApi by lazy { BulogResponse() }
    private val mPresenter by lazy { BulogPresenter(mApi, this) }
    private var mMonth: String? = null
    private var mYear: String? = null
    private var admin: Boolean = false
    private var idKancab: String? = null
    private val userRole by lazy { SpFactory(requireContext()).getAuthRole() }

    private val monthId = listOf(
        "01",
        "02",
        "03",
        "04",
        "05",
        "06",
        "07",
        "08",
        "09",
        "10",
        "11",
        "12"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bulog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()

        sp_year_bulog.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GlobalScope.launch(Dispatchers.Main) {
                    detachData()
                    mYear = sp_year_bulog.selectedItem.toString()
                    mPresenter.getDataByDate(
                        "${sp_year_bulog.selectedItem}-${monthId[sp_month_bulog.selectedItemPosition]}",
                        idKancab!!
                    )
                }
            }

        }

        sp_location_bulog.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GlobalScope.launch(Dispatchers.Main) {
                    detachData()
                    idKancab = (sp_location_bulog.selectedItemPosition + 1).toString()
                    mPresenter.getDataByDate(
                        "${sp_year_bulog.selectedItem}-${monthId[sp_month_bulog.selectedItemPosition]}",
                        idKancab!!
                    )
                }
            }

        }

        sp_month_bulog.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GlobalScope.launch(Dispatchers.Main) {
                    detachData()
                    mMonth = (monthId[sp_month_bulog.selectedItemPosition])
                    mPresenter.getDataByDate(
                        "${sp_year_bulog.selectedItem}-${monthId[sp_month_bulog.selectedItemPosition]}",
                        idKancab!!
                    )
                }
            }
        }

        sr_bulog.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Main) {
                detachData()
                mPresenter.getDataByDate(
                    "${sp_year_bulog.selectedItem}-${monthId[sp_month_bulog.selectedItemPosition]}",
                    idKancab!!
                )
            }
        }

        btn_add_data_bulog_admin.setOnClickListener {
            showViewAddData(null)
        }

        btn_total_bulog.setOnClickListener { showInfo() }
    }

    override fun showData(dataSource: ItemBulog) {
        with(rv_detail_bulog) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DataItemBulogAdapter(dataSource.x0 as List<ItemBulog.X0>) {
                if (admin) {
                    showOption(dataSource)
                }
            }
        }
    }

    override fun detachData() {
        rv_detail_bulog.adapter = null
    }

    override fun showLoading() {
        sr_bulog.isRefreshing = true
    }

    override fun hideLoading() {
        sr_bulog.isRefreshing = false
    }

    override fun showAddData() {
        if (admin) {
            btn_add_data_bulog_admin.visibility = View.VISIBLE
        }
    }

    override fun showViewAddData(dataSet: ItemBulog?) {
        findNavController().navigate(
            R.id.action_nav_bulog_to_bulogInput, bundleOf(
                Pair("idKancab", idKancab),
                Pair("tanggal", "$mYear-$mMonth"),
                Pair("dataSet", dataSet)
            )
        )
    }

    override fun showOption(dataSet: ItemBulog?) {
        MaterialDialog(requireContext()).show {
            title(text = "Pilihan Aksi")
            val myItems =
                listOf("Edit Data Tanggal Ini", "Hapus Semua Data Pada Tanggal Ini")
            listItems(items = myItems) { dialog, index, text ->
                if (index != 0) {
                    MaterialDialog(requireContext()).show {
                        title(text = "Apakah Anda Yakin Ingin Menghapus Data Pada Tanggal Ini ?")
                        message(text = "Data yang telah dihapus tidak dapat dipulihkan kembali")
                        positiveButton(text = "Hapus Data") {
                            GlobalScope.launch(Dispatchers.Main) {
                                dataSet?.x0?.get(0)?.idData?.let { it1 -> mPresenter.deleteData(it1) }
                            }
                        }
                        negativeButton(text = "Kembali")
                    }
                } else {
                    showViewAddData(dataSet)
                }
            }
        }
    }


    override fun hideAddData() {
        btn_add_data_bulog_admin.visibility = View.GONE
    }

    override fun showError(msg: String) {
        tv_admin_bulog_desc.text = msg
        tv_admin_bulog_desc.visibility = View.VISIBLE
    }

    override fun hideError() {
        tv_admin_bulog_desc.visibility = View.GONE
    }

    private fun initialize() {
        val month = listOf(
            "Januari",
            "Februari",
            "Maret",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Agustus",
            "September",
            "Oktober",
            "November",
            "Desember"
        )

        val years = ArrayList<String>()
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        val thisMonth: Int = Calendar.getInstance().get(Calendar.MONTH)
        for (i in 2004..thisYear + 5) {
            years.add(i.toString())
        }

        val listKancabJson = Gson().fromJson(OfflineJson.Kancab, Kancab::class.java)
        val listKancab = arrayListOf<String>()

        for (i in listKancabJson.x0!!.indices) {
            listKancab.add("${listKancabJson.x0[i]!!.nama} (id : ${listKancabJson.x0[i]!!.id})")
        }

        sp_month_bulog.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            month
        )
        sp_month_bulog.setSelection(thisMonth)
        mMonth = (monthId[sp_month_bulog.selectedItemPosition])

        sp_year_bulog.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            years
        )
        sp_year_bulog.setSelection(
            (sp_year_bulog.adapter as ArrayAdapter<String?>).getPosition(
                thisYear.toString()
            )
        )
        mYear = sp_year_bulog.selectedItem.toString()

        sp_location_bulog.adapter = ArrayAdapter(
            requireContext(), R.layout.item_spinner_white,
            listKancab
        )
        idKancab = (sp_location_bulog.selectedItemPosition + 1).toString()

        if (userRole == "1" || userRole == "6") {
            admin = true
        }
    }

    private fun showInfo() {
        val dialog = MaterialDialog(requireContext()).customView(
            R.layout.layout_bulog_total_info,
            scrollable = true
        )
            .title(text = "Total Data ${sp_year_bulog.selectedItem}-${monthId[sp_month_bulog.selectedItemPosition]} di Jawa Timur")

        var data: BulogTotal
        GlobalScope.launch(Dispatchers.Main) {
            data =
                mPresenter.getTotalJatim("${sp_year_bulog.selectedItem}-${monthId[sp_month_bulog.selectedItemPosition]}")
            val cusView = dialog.getCustomView()
            try {
                cusView.et_beras_jatim.setText("${data.beras} Ton")
                cusView.et_gula_jatim.setText("${data.gulaPasir} Ton")
                cusView.et_jagung_jatim.setText("${data.jagung} Ton")
                cusView.et_minyak_jatim.setText("${data.minyakGoreng} Liter")
                cusView.et_tepung_jatim.setText("${data.tepungTerigu} Ton")
            } catch (e: Exception) {
                showError("Data tidak ditemukan")
            }
            joinAll()
        }
        dialog.show()
    }
}
