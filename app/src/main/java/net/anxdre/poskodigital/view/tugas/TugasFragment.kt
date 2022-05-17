package net.anxdre.poskodigital.view.tugas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tugas.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R

/**
 * A simple [Fragment] subclass.
 */
class TugasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tugas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            GlobalScope.launch(Dispatchers.Main) {
                textView2.startAnimation(
                    AnimationUtils.loadAnimation(
                        requireContext(),
                        R.anim.zoom_out
                    )
                )
                kotlin.runCatching {
                    delay(300L)
                    tugas_1.visibility = View.VISIBLE
                    tugas_1.startAnimation(
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.slide
                        )
                    )
                    delay(300L)
                    tugas_2.visibility = View.VISIBLE
                    tugas_2.startAnimation(
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.slide
                        )
                    )
                    delay(300L)
                    tugas_3.visibility = View.VISIBLE
                    tugas_3.startAnimation(
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.slide
                        )
                    )
                    delay(300L)
                    tugas_4.visibility = View.VISIBLE
                    tugas_4.startAnimation(
                        AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.slide
                        )
                    )
                }.onFailure { }
            }
        } catch (e: NullPointerException) {

        }
    }
}
