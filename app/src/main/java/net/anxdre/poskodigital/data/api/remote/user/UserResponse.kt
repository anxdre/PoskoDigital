package net.anxdre.poskodigital.data.api.remote.user

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.data.api.apiPostLoginAsync
import net.anxdre.poskodigital.data.api.model.user.User

@Suppress("UNCHECKED_CAST")
class UserResponse : UserRepository {
    override fun loginAsync(username: String, password: String): Deferred<User> =
        apiPostLoginAsync(
            "${BuildConfig.BASE_URL}check_login?",
            username,
            password
        ) as Deferred<User>
}