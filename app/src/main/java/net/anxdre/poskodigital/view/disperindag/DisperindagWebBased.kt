package net.anxdre.poskodigital.view.disperindag

import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_disperindag_web_based.*
import net.anxdre.poskodigital.R


class DisperindagWebBased : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_disperindag_web_based, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wv_perindag.loadUrl("http://siskaperbapo.com/")
        wv_perindag.settings.javaScriptEnabled = true
        wv_perindag.canGoBack()
        wv_perindag.webViewClient = WebViewClient()
        wv_perindag.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action == MotionEvent.ACTION_UP
                && wv_perindag.canGoBack()
            ) {
                wv_perindag.goBack()
                return@setOnKeyListener true
            }
            false
        }

    }


}