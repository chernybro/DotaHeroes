package com.chernybro.wb51.presentation.hero_list_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chernybro.wb51.data.remote.service.HeroListApi
import com.chernybro.wb51.domain.models.HeroItem
import com.chernybro.wb51.presentation.models.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesListViewModel @Inject constructor(
    private val heroListApi: HeroListApi
) : ViewModel() {
    private val _items: MutableLiveData<List<HeroItem>> = MutableLiveData(listOf())
    val items: LiveData<List<HeroItem>> = _items

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        fetchHeroes()
    }

    fun fetchHeroes(){
        viewModelScope.launch(Dispatchers.IO) {
            val state = heroListApi.getHeroes()
            Log.d("vm", "${state.error} .... data = ${state.data?.size}")
            when (state) {
                is ScreenState.Success -> _items.postValue(state.data)
                is ScreenState.Error -> _errorMessage.postValue(state.error)
            }
        }
    }
}