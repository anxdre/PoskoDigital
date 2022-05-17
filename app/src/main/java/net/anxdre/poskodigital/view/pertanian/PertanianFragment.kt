package net.anxdre.poskodigital.view.pertanian

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
import com.afollestad.materialdialogs.list.listItems
import kotlinx.android.synthetic.main.fragment_pertanian.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.adapter.DataItemPertanianAdapter
import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanian
import net.anxdre.poskodigital.data.api.model.pertanian.ItemPertanianInput
import net.anxdre.poskodigital.data.api.remote.pertanian.PertanianResponse
import net.anxdre.poskodigital.utils.SpFactory
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class PertanianFragment : Fragment(), PertanianView {
    private val mApi by lazy { PertanianResponse() }
    private val mPresenter by lazy { PertanianPresenter(mApi, this) }
    private var mTanggal: String? = null
    private var admin: Boolean = false
    private var idData: String? = null
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
        return inflater.inflate(R.layout.fragment_pertanian, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()

        sp_year_pertanian.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GlobalScope.launch(Dispatchers.Main) {
                    detachData()
                    mTanggal =
                        "${sp_year_pertanian.selectedItem}-${monthId[sp_month_pertanian.selectedItemPosition]}"
                    mPresenter.getDataByDate(
                        mTanggal!!
                    )
                }
            }

        }

        sp_month_pertanian.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                GlobalScope.launch(Dispatchers.Main) {
                    detachData()
                    mTanggal =
                        "${sp_year_pertanian.selectedItem}-${monthId[sp_month_pertanian.selectedItemPosition]}"
                    mPresenter.getDataByDate(
                        mTanggal!!
                    )
                }
            }
        }

        sr_pertanian.setOnRefreshListener {
            detachData()
            GlobalScope.launch(Dispatchers.Main) {
                mTanggal =
                    "${sp_year_pertanian.selectedItem}-${monthId[sp_month_pertanian.selectedItemPosition]}"
                mPresenter.getDataByDate(
                    mTanggal!!
                )
            }
        }


        btn_add_data_pertanian_admin.setOnClickListener {
            showViewAddData(null)
        }
    }

    override fun showData(dataSource: ItemPertanian) {
        idData = dataSource.x0?.get(0)?.idData!!
        tv_id_data_pertanian.text = idData

        with(rv_detail_pertanian) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DataItemPertanianAdapter(dataSource.x0 as List<ItemPertanian.X0>) {
                if (admin) {
                    showOption(
                        ItemPertanianInput(
                            it.id, it.idData, it.idKomoditas!!, it.sisaStok!!, it.tahunBulan!!,
                            it.produksi!!,
                            it.konsumsi!!
                        )
                    )
                }
            }
        }
    }

    override fun detachData() {
        tv_id_data_pertanian.text = null
        idData = null
        rv_detail_pertanian.adapter = null
    }

    override fun showLoading() {
        sr_pertanian.isRefreshing = true
    }

    override fun hideLoading() {
        sr_pertanian.isRefreshing = false
    }

    override fun showAddData() {
        if (admin) {
            btn_add_data_pertanian_admin.visibility = View.VISIBLE
        }
    }

    override fun showViewAddData(dataSet: ItemPertanianInput?) {
        findNavController().navigate(
            R.id.action_pertanianFragment_to_nav_pertanian_input,
            bundleOf(
                Pair("dataSet", dataSet),
                Pair("tanggal", mTanggal!!),
                Pair("idData", idData),
                Pair("selectedIdItem", dataSet?.idKomoditas)
            )
        )
    }

    override fun showOption(dataSet: ItemPertanianInput?) {
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
                                if (dataSet != null) {
                                    dataSet.idData?.let { it1 -> mPresenter.deleteData(it1) }
                                }
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

    override fun showError(msg: String) {
        tv_admin_pertanian_desc.visibility = View.VISIBLE
        tv_admin_pertanian_desc.text = msg
    }

    override fun hideError() {
        tv_admin_pertanian_desc.visibility = View.GONE
    }

    override fun showIdData(idData: String) {
        tv_id_data_pertanian.text = idData
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

        sp_month_pertanian.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            month
        )
        sp_month_pertanian.setSelection(thisMonth)

        sp_year_pertanian.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            years
        )
        sp_year_pertanian.setSelection(
            (sp_year_pertanian.adapter as ArrayAdapter<String?>).getPosition(
                thisYear.toString()
            )
        )

        if (mTanggal == null) {
            mTanggal =
                "${sp_year_pertanian.selectedItem}-${monthId[sp_month_pertanian.selectedItemPosition]}"
        }

        if (userRole == "1" || userRole == "4") {
            admin = true
            btn_add_data_pertanian_admin.visibility = View.VISIBLE
        }
    }
}
