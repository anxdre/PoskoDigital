package net.anxdre.poskodigital.data.api

import android.accounts.NetworkErrorException
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.data.api.model.user.User
import java.io.File


fun apiPostRequestAnyAsync(
    url: String?,
    type: Any
): Deferred<Any> {
    return GlobalScope.async {
        val response = AndroidNetworking
            .post(url)
            .addHeaders("SP-API-KEY", BuildConfig.API_KEY)
            .setPriority(Priority.MEDIUM)
            .addBodyParameter(type)
            .build()
            .executeForString()

        if (!response.isSuccess) {
            throw NetworkErrorException(response.error.localizedMessage)
        }
        response.result
    }
}

fun uploadFileAnyAsync(url: String, imgFile: File, type: Any): Deferred<Any> {
    return GlobalScope.async {
        val response =
            AndroidNetworking.upload("${BuildConfig.BASE_URL}$url")
                .addHeaders("SP-API-KEY", BuildConfig.API_KEY)
                .addMultipartParameter(type)
                .addMultipartFile("gambar", imgFile)
                .setPriority(Priority.HIGH)
                .build()
                .executeForString()

        if (!response.isSuccess) {
            throw NetworkErrorException(response.error.localizedMessage)
        }
        response.result
    }
}

fun apiPostBasicRequestAsync(
    url: String?,
    type: Class<*>,
    param1: Pair<String, String>?,
    param2: Pair<String, String>?
): Deferred<Any> {
    return GlobalScope.async {
        val response = AndroidNetworking
            .post(url)
            .addHeaders("SP-API-KEY", BuildConfig.API_KEY)
            .addBodyParameter(param1?.first, param1?.second)
            .addBodyParameter(param2?.first, param2?.second)
            .build()
            .executeForObject(type)

        if (!response.isSuccess) {
            throw NetworkErrorException(response.error.localizedMessage)
        }

        response.result
    }
}

fun apiPostRequestStringAsync(
    url: String?,
    param1: Pair<String, String>?,
    param2: Pair<String, String>?,
    param3: Pair<String, String>?,
    param4: Pair<String, String>?,
    param5: Pair<String, String>?,
    param6: Pair<String, String>?,
    param7: Pair<String, String>?
): Deferred<Any> {
    return GlobalScope.async {
        val response = AndroidNetworking
            .post(url)
            .addHeaders("SP-API-KEY", BuildConfig.API_KEY)
            .addBodyParameter(param1?.first, param1?.second)
            .addBodyParameter(param2?.first, param2?.second)
            .addBodyParameter(param3?.first, param3?.second)
            .addBodyParameter(param4?.first, param4?.second)
            .addBodyParameter(param5?.first, param5?.second)
            .addBodyParameter(param6?.first, param6?.second)
            .addBodyParameter(param7?.first, param7?.second)
            .build()
            .executeForString()

        if (!response.isSuccess) {

            throw NetworkErrorException(response.error.localizedMessage)
        }

        response.result
    }
}

fun apiPostManyRequestTypeAsync(
    url: String?,
    param1: Pair<String, String>?,
    param2: Pair<String, String>?,
    param3: Pair<String, String>?,
    param4: Pair<String, String>?,
    param5: Pair<String, String>?,
    param6: Pair<String, String>?,
    param7: Pair<String, String>?,
    type: Class<*>
): Deferred<Any> {
    return GlobalScope.async {
        val response = AndroidNetworking
            .post(url)
            .addHeaders("SP-API-KEY", BuildConfig.API_KEY)
            .addBodyParameter(param1?.first, param1?.second)
            .addBodyParameter(param2?.first, param2?.second)
            .addBodyParameter(param3?.first, param3?.second)
            .addBodyParameter(param4?.first, param4?.second)
            .addBodyParameter(param5?.first, param5?.second)
            .addBodyParameter(param6?.first, param6?.second)
            .addBodyParameter(param7?.first, param7?.second)
            .build()
            .executeForObject(type)

        if (!response.isSuccess) {

            throw NetworkErrorException(response.error.localizedMessage)
        }

        response.result
    }
}

fun apiPostLoginAsync(url: String?, username: String, password: String): Deferred<Any> {
    return GlobalScope.async {
        val response = AndroidNetworking
            .post(url)
            .addHeaders("SP-API-KEY", BuildConfig.API_KEY)
            .addBodyParameter("username", username)
            .addBodyParameter("password", password)
            .build()
            .executeForObject(User::class.java)

        if (!response.isSuccess) {
            throw NetworkErrorException(response.error.localizedMessage)
        }
        response.result
    }
}

fun apiGetImageAsync(url: String?, parameter: Any, type: Class<*>): Deferred<Any> {
    return GlobalScope.async {
        val response = AndroidNetworking
            .get(url)
            .addHeaders("SP-API-KEY", BuildConfig.API_KEY)
            .addQueryParameter(parameter)
            .build()
            .executeForObject(type)

        if (!response.isSuccess) {
            throw NetworkErrorException(response.error.localizedMessage)
        }
        response.result
    }
}



