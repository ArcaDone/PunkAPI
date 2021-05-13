package com.arcadone.cheers

import android.app.Application
import com.arcadone.cheers.api.BeerService
import com.arcadone.cheers.database.BeerDatabase
import com.arcadone.cheers.repository.BeerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CheersApp : Application() {

    companion object {
        var instance = CheersApp()
    }

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { BeerDatabase.getInstance(this, applicationScope) }
    val beerApi = BeerService.create()
    val repository by lazy { BeerRepository(database.beerDao(), beerApi) }
}