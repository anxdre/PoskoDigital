package net.anxdre.poskodigital.view.perkebunan

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
import kotlinx.android.synthetic.main.fragment_perkebunan_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.adapter.DataGulaItemAdapter
import net.anxdre.poskodigital.data.api.model.perkebunan.PerkebunanGula
import net.anxdre.poskodigital.data.api.remote.perkebunan.PerkebunanGulaResponse
import net.anxdre.poskodigital.utils.SpFactory
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class PerkebunanFragment : Fragment(), PerkebunanView {
    private val mApi by lazy { PerkebunanGulaResponse() }
    private val mPresenter by lazy { PerkebunanPresenter(mApi, this) }
    private var mMonth: String? = null
    private var mYear: String? = null
    private var admin: Boolean = false
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
        return inflater.inflate(R.layout.fragment_perkebunan_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()

        sp_year_perkebunan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                    mYear = sp_year_perkebunan.selectedItem.toString()
                    mPresenter.getDataByDate(
                        "${sp_year_perkebunan.selectedItem}-${monthId[sp_month_perkebunan.selectedItemPosition]}"
                    )
                }
            }

        }

        sp_month_perkebunan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                    mMonth = (monthId[sp_month_perkebunan.selectedItemPosition])
                    mPresenter.getDataByDate(
                        "${sp_year_perkebunan.selectedItem}-${monthId[sp_month_perkebunan.selectedItemPosition]}"
                    )
                }
            }
        }

        sr_perkebunan.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Main) {
                detachData()
                mPresenter.getDataByDate(
                    "${sp_year_perkebunan.selectedItem}-${monthId[sp_month_perkebunan.selectedItemPosition]}"
                )
            }
        }

        btn_add_data_perkebunan_admin.setOnClickListener {
            showViewAddData(null)
        }
    }

    override fun showData(dataSource: PerkebunanGula) {
        with(rv_detail_perkebunan) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dataSource.x0?.let { it ->
                DataGulaItemAdapter(it) {
                    if (admin) showOption(dataSource)
                }
            }
        }
    }

    override fun detachData() {
        tv_total_perkebunan.text = "0 Ton"
        tv_consumpution_perkebunan.text = "0 Ton"
        tv_devisit_perkebunan.text = "0 Ton"
        rv_detail_perkebunan.adapter = null
    }

    override fun showSubData(total: String?, consumption: String?, devisit: String?) {
        tv_total_perkebunan.text = "$total Ton"
        tv_consumpution_perkebunan.text = "$consumption Ton"
        tv_devisit_perkebunan.text = "$devisit Ton"
    }

    override fun showLoading() {
        sr_perkebunan.isRefreshing = true
    }

    override fun hideLoading() {
        sr_perkebunan.isRefreshing = false
    }

    override fun showError(msg: String) {
        tv_admin_perkebunan_desc.text = msg
        tv_admin_perkebunan_desc.visibility = View.VISIBLE
    }

    override fun hideError() {
        tv_admin_perkebunan_desc.visibility = View.GONE
    }

    override fun showAddData() {
        if (admin) {
            btn_add_data_perkebunan_admin.visibility = View.VISIBLE
        }
    }

    override fun showViewAddData(dataSet: PerkebunanGula?) {
        findNavController().navigate(
            R.id.action_nav_perkebunan_to_nav_edit_perkebunan, bundleOf(
                Pair("dataSet", dataSet),
                Pair("tanggal", "$mYear-$mMonth")
            )
        )
    }

    override fun hideAddData() {
        btn_add_data_perkebunan_admin.visibility = View.GONE
    }

    override fun showOption(dataSet: PerkebunanGula?) {
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
                                dataSet?.x0?.get(0)?.idPemakaian?.let { it1 ->
                                    mPresenter.deleteData(
                                        it1
                                    )
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

        sp_month_perkebunan.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            month
        )
        sp_month_perkebunan.setSelection(thisMonth)
        mMonth = (monthId[sp_month_perkebunan.selectedItemPosition])

        sp_year_perkebunan.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            years
        )
        sp_year_perkebunan.setSelection(
            (sp_year_perkebunan.adapter as ArrayAdapter<String?>).getPosition(
                thisYear.toString()
            )
        )
        mYear = sp_year_perkebunan.selectedItem.toString()

        if (userRole == "1" || userRole == "3") {
            admin = true
        }
    }

}
