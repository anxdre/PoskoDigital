package net.anxdre.poskodigital.view.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.anxdre.poskodigital.R
import net.anxdre.poskodigital.data.api.remote.user.UserResponse
import net.anxdre.poskodigital.utils.SpFactory

class LoginActivity : AppCompatActivity(), LoginView {
    private val mApi by lazy { UserResponse() }
    private val mPresenter by lazy { LoginPresenter(this, mApi) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val spFactory = SpFactory(baseContext)
        val idUser = spFactory.getAuthId()
        var auth = false

        if (idUser != "false") {
            auth = true
            textView.text = "Data Profil"
            et_username.setText(spFactory.getAuthName())
            et_username.isEnabled = false
            et_username.isFocusable = false
            textInputLayout2.visibility = View.GONE
            btn_login.text = "Logout"
        }

        btn_login.setOnClickListener {
            if (auth) {
                deleteCredentials()
            } else {
                if (nonEmptyList(et_username, et_password) { view, msg ->
                        view.error = msg
                    }) {
                    GlobalScope.launch(Dispatchers.Main) {
                        mPresenter.reqLogin(
                            et_username.text.toString(),
                            et_password.text.toString()
                        )
                    }
                }

            }
        }
    }

    override fun saveCredentials(id: String, username: String, kota: String, role: String) {
        SpFactory(baseContext).saveAuth(id, username, kota, role)
    }

    override fun deleteCredentials() {
        SpFactory(baseContext).deleteAuth()
        showMainMenu()
    }

    override fun showMainMenu() {
        finish()
    }

    override fun showLoading() {
        pb_login.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_login.visibility = View.INVISIBLE
    }

    override fun showError(errorMsg: String) {
        MaterialDialog(this).show {
            title(text = "Error")
            message(text = errorMsg)
        }
    }
}
