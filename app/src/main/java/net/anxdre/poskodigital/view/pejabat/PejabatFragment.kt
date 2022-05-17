package net.anxdre.poskodigital.view.pejabat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_pejabat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R

/**
 * A simple [Fragment] subclass.
 */
class PejabatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pejabat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                tv_ketua_title.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.zoom_out
                    )
                )
                delay(350L)
                iv_ketua.visibility = View.VISIBLE
                iv_ketua.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.zoom_out
                    )
                )
                delay(500L)

                ll_anggota_row1.visibility = View.VISIBLE
                ll_anggota_row1.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.slide
                    )
                )
                delay(500L)

                ll_anggota_row2.visibility = View.VISIBLE
                ll_anggota_row2.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.slide
                    )
                )
                delay(500L)

                ll_anggota_row3.visibility = View.VISIBLE
                ll_anggota_row3.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.slide
                    )
                )

            } catch (e: Exception) {

            }
        }
    }

}
