package com.arcadone.cheers.ui.mainFragment

import androidx.lifecycle.*
import com.arcadone.cheers.model.BeerItem
import com.arcadone.cheers.repository.BeerRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import com.arcadone.cheers.model.Result
import kotlinx.coroutines.flow.collect
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@InternalCoroutinesApi
class MainViewModel(private val beerRepository: BeerRepository) : ViewModel() {
    var calendarInstance = Calendar.getInstance()
    var brewedBefore = MutableLiveData("")
    var brewedAfter = MutableLiveData("")
    var page = 1

    private val _navigateToDetail = MutableLiveData<BeerItem>()
    val navigateToDetail: LiveData<BeerItem> = _navigateToDetail

    private var _uiState = MutableLiveData<Result<List<BeerItem>>>()
    val uiState: LiveData<Result<List<BeerItem>>> = _uiState

    init {
        updateUiState()
    }

    fun updateUiState() {

        viewModelScope.launch {
            beerRepository.getBeers(brewedBefore.value!!, brewedAfter.value!!, page).collect {
                _uiState.value = it
            }
        }
    }

    fun displayPropertyDetails(item: BeerItem) {
        _navigateToDetail.postValue(item)
    }
}
