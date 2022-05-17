package net.anxdre.poskodigital.data.api.remote.user

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.data.api.model.user.User

interface UserRepository {
    fun loginAsync(username: String, password: String): Deferred<User>
}