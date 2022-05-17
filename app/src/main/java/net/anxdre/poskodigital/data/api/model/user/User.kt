package net.anxdre.poskodigital.data.api.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("0")
    val x0: X0?
) {
    data class X0(
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("id_kota")
        val idKota: String?,
        @SerializedName("id_role")
        val idRole: String?,
        @SerializedName("last_ip")
        val lastIp: String?,
        @SerializedName("last_login")
        val lastLogin: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("username")
        val username: String?
    )
}