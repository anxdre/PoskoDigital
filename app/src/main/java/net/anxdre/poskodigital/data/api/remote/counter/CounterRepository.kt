package net.anxdre.poskodigital.data.api.remote.counter

import kotlinx.coroutines.Deferred
import net.anxdre.poskodigital.data.api.model.counter.CounterVisitor

interface CounterRepository {
    fun getTotalVisitor(): Deferred<CounterVisitor>
    fun postTotalVisitor(ipAddress: String): Deferred<String>
}