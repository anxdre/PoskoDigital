package net.anxdre.poskodigital.view

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        GlobalScope.launch(Dispatchers.Main) {
            imageView.startAnimation(
                AnimationUtils.loadAnimation(
                    applicationContext,
                    R.anim.zoom_out
                )
            )
            delay(1800L)
            startActivity(Intent(baseContext, NavigationMenuActivity::class.java))
            finish()
        }
    }
}
