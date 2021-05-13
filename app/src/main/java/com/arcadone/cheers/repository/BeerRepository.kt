package com.arcadone.cheers.repository

import com.arcadone.cheers.api.BaseDataSource
import com.arcadone.cheers.api.BeerApi
import com.arcadone.cheers.database.BeerDao
import com.arcadone.cheers.model.BeerItem
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import com.arcadone.cheers.model.Result

class BeerRepository(private val beerDao: BeerDao, private val beerApi: BeerApi) :
    BaseDataSource() {

    @InternalCoroutinesApi
    suspend fun getBeers(
        brewedBefore: String,
        brewedAfter: String,
        page: Int
    ): Flow<Result<List<BeerItem>>> {
        return if (brewedBefore.isEmpty() && brewedAfter.isEmpty()) {
            loadBeers(
                loadFromDb = { beerDao.getBeers() },
                createCall = { getResult { beerApi.getBeers(page) } },
                saveCallResult = { beerDao.insertBeers(it) }
            )
        } else if (brewedBefore.isEmpty()) {
            loadBeers(
                loadFromDb = {
                    beerDao.getBeersByDate(brewedAfter, "01/2050")
                },
                createCall = {
                    getResult {
                        beerApi.getBeersByBrewedDate(
                            page = page,
                            brewedAfter = brewedAfter,
                            brewedBefore = "01/2050"
                        )
                    }
                },
                saveCallResult = { beerDao.insertBeers(it) }
            )
        } else if (brewedAfter.isEmpty()) {
            loadBeers(
                loadFromDb = {
                    beerDao.getBeersByDate("01/1900", brewedBefore)
                },
                createCall = {
                    getResult {
                        beerApi.getBeersByBrewedDate(
                            page = page,
                            brewedAfter = "01/1900",
                            brewedBefore = brewedBefore
                        )
                    }
                },
                saveCallResult = { beerDao.insertBeers(it) }
            )
        } else {
            loadBeers(
                loadFromDb = {
                    beerDao.getBeersByDate(brewedAfter, brewedBefore)
                },
                createCall = {
                    getResult {
                        beerApi.getBeersByBrewedDate(
                            page = page,
                            brewedAfter = brewedAfter,
                            brewedBefore = brewedBefore
                        )
                    }
                },
                saveCallResult = { beerDao.insertBeers(it) }
            )
        }
    }
}
