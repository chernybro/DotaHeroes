package com.chernybro.wb51.data.remote.service

import com.chernybro.wb51.presentation.models.ScreenState
import com.chernybro.wb51.domain.models.HeroItem

interface HeroListApi {
    suspend fun getHeroes(): ScreenState<List<HeroItem>>
}