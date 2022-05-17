package net.anxdre.poskodigital.utils

import android.content.Context
import android.content.SharedPreferences

class SpFactory(context: Context) {
    private val sharedPref: SharedPreferences = context.getSharedPreferences("APP", 0)

    fun saveAuth(id: String, username: String, kota: String, role: String) {
        with(sharedPref.edit()) {
            putString("id", id)
            putString("kota", kota)
            putString("username", username)
            putString("role", role)
            commit()
        }
    }

    fun getAuthId(): String? {
        return sharedPref.getString("id", "false")
    }

    fun getAuthCity(): String? {
        return sharedPref.getString("kota", "false")
    }

    fun getAuthName(): String? {
        return sharedPref.getString("username", "false")
    }

    fun getAuthRole(): String? {
        return sharedPref.getString("role", "false")
    }

    fun deleteAuth() {
        with(sharedPref.edit()) {
            putString("id", "false")
            putString("kota", "false")
            putString("username", "false")
            putString("role", "false")
            commit()
        }
    }
}