package net.anxdre.poskodigital.data.api.remote.counter

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.BuildConfig
import net.anxdre.poskodigital.data.api.apiGetImageAsync
import net.anxdre.poskodigital.data.api.apiPostRequestStringAsync
import net.anxdre.poskodigital.data.api.model.counter.CounterVisitor

class CounterResponse : CounterRepository {
    override fun getTotalVisitor(): Deferred<CounterVisitor> =
        apiGetImageAsync(
            "${BuildConfig.BASE_URL}result_visitor",
            " ",
            CounterVisitor::class.java
        ) as Deferred<CounterVisitor>

    override fun postTotalVisitor(ipAddress: String): Deferred<String> =
        apiPostRequestStringAsync(
            "${BuildConfig.BASE_URL}input_visitor",
            Pair("ip_address", ipAddress),
            Pair("", ""),
            Pair("", ""),
            Pair("", ""),
            Pair("", ""),
            Pair("", ""),
            Pair("", "")
        ) as Deferred<String>
}