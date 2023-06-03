package net.anxdre.poskodigital.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main_menu.*
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.utils.SpFactory
import net.anxdre.poskodigital.view.login.LoginActivity


class NavigationMenuActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var ivProfile: CircleImageView
    private lateinit var tvUserName: AppCompatTextView
    private lateinit var spFactory: SpFactory
    private lateinit var headerView: View
    private lateinit var idUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        headerView = nav_view.getHeaderView(0)
        ivProfile = headerView.findViewById(R.id.iv_profile)
        tvUserName = headerView.findViewById(R.id.tv_username)
        spFactory = SpFactory(baseContext)
        idUser = spFactory.getAuthId()!!
        loadNav()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.setGraph(R.navigation.nav_mainmenu)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_main_menu)
        NavigationUI.setupWithNavController(nav_view, navController)

        ivProfile.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            onPause()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawer_main_menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (drawer_main_menu.isDrawerOpen(GravityCompat.START)) {
            drawer_main_menu.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun loadNav() {
        if (idUser != "false") {
            tvUserName.text = spFactory.getAuthName()
        } else {
            tvUserName.text = "Tamu"
        }
    }

    override fun onResume() {
        super.onResume()
        idUser = spFactory.getAuthId()!!
        loadNav()
    }


}
