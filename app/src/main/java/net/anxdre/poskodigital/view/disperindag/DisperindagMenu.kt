package net.anxdre.poskodigital.view.disperindag

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.fragment_disperindag_menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.adapter.DataDetailPerindagAdapter
import net.anxdre.poskodigital.data.api.model.disperindag.DataDetailPerindagItem
import net.anxdre.poskodigital.data.api.remote.disperindag.PerindagResponse
import net.anxdre.poskodigital.utils.SpFactory
import java.text.SimpleDateFormat
import java.util.*

class DisperindagMenu : Fragment(), DisperindagView {
    private val mApi by lazy { PerindagResponse() }
    private val mPresenter by lazy { DisperindagPresenter(this, mApi) }

    @SuppressLint("SimpleDateFormat")
    private val df = SimpleDateFormat("yyyy-MM-dd")
    private val yesCalendar: Calendar = Calendar.getInstance()
    private var idTanggal: String? = null
    private var idTanggalYesterday: String? = null
    private var idKomuditas: String? = null
    private var namKomuditas: String? = null
    private var idSort: Int? = 0
    private var namSort: String? = null
    private var idAuth: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        idAuth = SpFactory(requireContext()).getAuthRole()
        idTanggal = arguments?.getString("id_tanggal")
        idTanggalYesterday = arguments?.getString("id_tanggal2")
        idKomuditas = arguments?.getString("id_komuditas")
        idSort = arguments?.getInt("sort")
        namKomuditas = arguments?.getString("namKomuditas")
        namSort = arguments?.getString("namSort")

        if (idKomuditas == null && idTanggal == null && idTanggalYesterday == null) {
            yesCalendar.add(Calendar.DATE, -1)
            idKomuditas = "1"
            idTanggal = df.format(Calendar.getInstance().time)
            idTanggalYesterday = df.format(yesCalendar.time)
            namKomuditas = "Beras Medium"
            namSort = "Tertinggi"
        }

        return inflater.inflate(R.layout.fragment_disperindag_menu, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        tv_new_date.text = idTanggal
//        tv_last_date.text = idTanggalYesterday
//        tv_cat_komuditas.text = namKomuditas
//        tv_cat_sort.text = namSort
//
//        if (idAuth == "1" || idAuth == "2") {
//            btn_admin_disperindag.visibility = View.VISIBLE
//        }
//
//        getData()
//
//        btn_admin_disperindag.setOnClickListener {
//            findNavController().navigate(R.id.action_nav_disperindag_to_nav_disperindag_admin)
//        }
//
//        btn_filter_perindag.setOnClickListener {
//            showFilter()
//        }
//
//        sp_perindag.setOnRefreshListener { getData() }
//    }

    override fun showMainMenu() {
        findNavController().navigateUp()
    }

    override fun showLoading() {
        sp_perindag.isRefreshing = true
    }

    override fun hideLoading() {
        sp_perindag.isRefreshing = false
    }

    override fun showLastestData(dataSource: List<DataDetailPerindagItem>) {
        with(rv_updated_data) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = (DataDetailPerindagAdapter(dataSource) {

            })
        }
    }

    override fun showLastData(dataSource: List<DataDetailPerindagItem>) {
        with(rv_last_data) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = (DataDetailPerindagAdapter(dataSource) {

            })
        }
    }

    override fun showError(errorMsg: String) {
        MaterialDialog(requireContext()).show {
            title(text = "Data Tidak Ditemukan")
            message(text = "$errorMsg😕")
        }
    }

    override fun showFilter() {
//        findNavController().navigate(R.id.action_nav_disperindag_to_filterFragment)
    }

    private fun getData() {
        if (idSort != 0) {
            GlobalScope.launch(Dispatchers.Main) {
                mPresenter.getLastLowData(
                    idKomuditas!!,
                    idTanggalYesterday!!
                )
            }
            GlobalScope.launch(Dispatchers.Main) {
                mPresenter.getLastestLowData(
                    idKomuditas!!,
                    idTanggal!!
                )
            }
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                mPresenter.getLastestHighData(
                    idKomuditas!!,
                    idTanggal!!
                )
            } // data baru

            GlobalScope.launch(Dispatchers.Main) {
                mPresenter.getLastHighData(
                    idKomuditas!!,
                    idTanggalYesterday!!
                )
            } // data kemarin
        }
    }
}