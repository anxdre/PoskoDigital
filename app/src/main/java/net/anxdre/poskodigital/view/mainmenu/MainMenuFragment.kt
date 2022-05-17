package net.anxdre.poskodigital.view.mainmenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main_menu.*
import kotlinx.coroutines.*
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.adapter.DataKegiatanAdapter
import net.anxdre.poskodigital.data.api.apiGetImageAsync
import net.anxdre.poskodigital.data.api.model.kegiatan.KegiatanItem
import net.anxdre.poskodigital.data.api.remote.counter.CounterResponse
import org.imaginativeworld.whynotimagecarousel.CarouselItem


/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : Fragment() {

    private var listImage = mutableListOf<CarouselItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        GlobalScope.launch(Dispatchers.Main) {
            sr_main.isRefreshing = true
            delay(300L)
            countVisitor()
            getImageNews()
//            getLastestNews()
            showNews()
            joinAll()
            try {
                cv_main.addData(listImage)
            } catch (e: Exception) {

            }
            sr_main.isRefreshing = false
        }

        sr_main.setOnRefreshListener {
            GlobalScope.launch(Dispatchers.Main) {
                countVisitor()
                getImageNews()
//                getLastestNews()
                showNews()
                joinAll()
                try {
                    cv_main.addData(listImage)
                } catch (e: Exception) {
                }
                sr_main.isRefreshing = false
            }
        }
    }

    private suspend fun getImageNews() {
        try {
            listImage.clear()
            val a: KegiatanItem = apiGetImageAsync(
                "${BuildConfig.BASE_URL}dokumentasi_latest",
                "",
                KegiatanItem::class.java
            ).await() as KegiatanItem
            for (i in a.x0!!.indices) {
                listImage.add(
                    CarouselItem(
                        "http://poskodigitalsatgaspangan.net/assets/images/dokumentasi/${a.x0[i]?.gambar!!}",
                        a.x0[i]?.judul
                    )
                )
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Terjadi Kesalahan Memuat Berita", Toast.LENGTH_SHORT)
                .show()
        }

    }

//    private suspend fun getLastestNews() {
//        try {
//            val a: DataDetailPerindag = apiGetImage(
//                "${BuildConfig.BASE_URL}item_perindag_get_highlow?",
//                " ",
//                DataDetailPerindag::class.java
//            ).await() as DataDetailPerindag
//
//            if (a.isNotEmpty()) {
//                tv_barang_up.text = a[0].namaKomoditas
//                tv_desc_up.text = "Harga Tertinggi di Kabupaten ${a[0].namaKota}"
//                tv_harga_up.text = "Rp. ${a[0].harga}"
//
//                tv_barang_down.text = a[1].namaKomoditas
//                tv_desc_down.text = "Harga Tertinggi di Kabupaten ${a[1].namaKota}"
//                tv_harga_down.text = "Rp. ${a[1].harga}"
//            }
//
//        } catch (e: Exception) {
//
//        }
//
//    }

    private suspend fun showNews() {
        try {
            val response: KegiatanItem =
                apiGetImageAsync(
                    "${BuildConfig.BASE_URL}dokumentasi_latest",
                    " ",
                    KegiatanItem::class.java
                ).await() as KegiatanItem

            with(rv_updated_news) {
                layoutManager = LinearLayoutManager(context)
                adapter = DataKegiatanAdapter(response.x0 as List<KegiatanItem.X0>) {
                    val a = Intent(Intent.ACTION_VIEW)
                    a.data =
                        Uri.parse("http://poskodigitalsatgaspangan.net/main/dokumentasi/detail/${it.id}")
                    startActivity(a)
                }
            }

            rv_updated_news.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.slide
                )
            )
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Terjadi Kesalahan Memuat Berita", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private suspend fun countVisitor() {
        val visitorFactory = CounterResponse()
        try {
            visitorFactory.postTotalVisitor("127.0.0.1")
            val visitorCount = visitorFactory.getTotalVisitor().await()
            tv_count_day.text = ": ${visitorCount.now}"
            tv_count_total.text = ": ${visitorCount.total}"
        } catch (e: java.lang.Exception) {

        }
    }

    override fun onStop() {
        super.onStop()
        MainScope().cancel()
    }

}
