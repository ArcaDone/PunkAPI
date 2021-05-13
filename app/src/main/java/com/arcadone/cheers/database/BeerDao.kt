package com.arcadone.cheers.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arcadone.cheers.model.BeerItem

@Dao
interface BeerDao {

    @Query("SELECT * FROM beeritem")
    fun getBeers(): List<BeerItem>

    @Query("SELECT * FROM beeritem WHERE beeritem.firstBrewed BETWEEN :dateStart AND :dateEnd")
    suspend fun getBeersByDate(dateStart: String, dateEnd: String): List<BeerItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeers(beers: List<BeerItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeer(beers: BeerItem)

}
