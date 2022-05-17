package net.anxdre.poskodigital.view.peternakan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import kotlinx.android.synthetic.main.fragment_peternakan.*
import kotlinx.android.synthetic.main.layout_item_peternakan.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.model.peternakan.PeternakanItem
import net.anxdre.poskodigital.data.api.remote.peternakan.PeternakanResponse
import net.anxdre.poskodigital.utils.SpFactory
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class PeternakanFragment : Fragment(), PeternakanView {
    private val mApi by lazy { PeternakanResponse() }
    private val mPresenter by lazy { PeternakanPresenter(this, mApi) }
    private var mTanggal: String? = null
    private var admin: Boolean = false
    private var dataSet: PeternakanItem.X0? = null
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
        return inflater.inflate(R.layout.fragment_peternakan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()

        sp_year_peternakan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                        "${sp_year_peternakan.selectedItem}-${monthId[sp_month_peternakan.selectedItemPosition]}"
                    mPresenter.getData(
                        mTanggal!!
                    )
                }
            }

        }

        sp_month_peternakan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                        "${sp_year_peternakan.selectedItem}-${monthId[sp_month_peternakan.selectedItemPosition]}"
                    mPresenter.getData(
                        mTanggal!!
                    )
                }
            }
        }

        sr_peternakan.setOnRefreshListener {
            detachData()
            GlobalScope.launch(Dispatchers.Main) {
                mTanggal =
                    "${sp_year_peternakan.selectedItem}-${monthId[sp_month_peternakan.selectedItemPosition]}"
                mPresenter.getData(
                    mTanggal!!
                )
            }
        }


        btn_add_data_peternakan_admin.setOnClickListener {
            showViewAddData(null)
        }

        btn_option_peternakan.setOnClickListener {
            dataSet?.let { it1 -> showOption(it1) }
        }
    }

    override fun detachData() {
        btn_option_peternakan.visibility = View.GONE
        ll_data_peternakan.visibility = View.GONE
    }

    override fun showLoading() {
        sr_peternakan.isRefreshing = true
    }

    override fun hideLoading() {
        sr_peternakan.isRefreshing = false
    }

    override fun showAddData() {
        if (admin) {
            btn_add_data_peternakan_admin.visibility = View.VISIBLE
        }
    }

    override fun showError(msg: String) {
        tv_admin_peternakan_desc.visibility = View.VISIBLE
        tv_admin_peternakan_desc.text = msg
    }

    override fun hideError() {
        tv_admin_peternakan_desc.visibility = View.GONE
    }

    override fun hideAddData() {
        btn_add_data_peternakan_admin.visibility = View.GONE
    }

    override fun showData(dataSource: PeternakanItem.X0) {
        dataSet = dataSource
        ll_data_peternakan.visibility = View.VISIBLE

        if (admin) {
            btn_option_peternakan.visibility = View.VISIBLE
        }

        et_stok_sapi.setText(dataSource.ketersediaanSapi)
        et_total_requirement_sapi.setText(dataSource.kebutuhanSapi)
        et_total_stok_sapi.setText(dataSource.stokSapi)

        et_stok_ayam.setText(dataSource.ketersediaanAyam)
        et_total_requirement_ayam.setText(dataSource.kebutuhanAyam)
        et_total_stok_ayam.setText(dataSource.stokAyam)

        et_stok_telur.setText(dataSource.ketersediaanTelur)
        et_total_requirement_telur.setText(dataSource.kebutuhanTelur)
        et_total_stok_telur.setText(dataSource.stokTelur)
    }

    override fun showViewAddData(dataSet: PeternakanItem.X0?) {
        findNavController().navigate(
            R.id.action_nav_peternakan_to_peternakanInput, bundleOf(
                Pair("dataSet", dataSet),
                Pair("tanggal", mTanggal)
            )
        )
    }

    override fun showOption(dataSource: PeternakanItem.X0) {
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
                                dataSource.id?.let { it1 -> mPresenter.deleteData(it1) }
                            }
                        }
                        negativeButton(text = "Kembali")
                    }
                } else {
                    showViewAddData(dataSource)
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

        sp_month_peternakan.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            month
        )
        sp_month_peternakan.setSelection(thisMonth)

        sp_year_peternakan.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line,
            years
        )
        sp_year_peternakan.setSelection(
            (sp_year_peternakan.adapter as ArrayAdapter<String?>).getPosition(
                thisYear.toString()
            )
        )

        if (mTanggal == null) {
            mTanggal =
                "${sp_year_peternakan.selectedItem}-${monthId[sp_month_peternakan.selectedItemPosition]}"
        }

        if (userRole == "1" || userRole == "5") {
            admin = true
        }
    }
}
