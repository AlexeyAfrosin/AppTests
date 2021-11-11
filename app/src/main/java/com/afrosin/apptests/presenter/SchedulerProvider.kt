package com.afrosin.apptests.presenter

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}