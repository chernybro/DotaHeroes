package com.chernybro.wb51.data.remote.service

import com.chernybro.wb51.R
import com.chernybro.wb51.data.remote.models.HeroStatsDTO
import com.chernybro.wb51.data.remote.models.getHeroDetailsFromDTO
import com.chernybro.wb51.data.remote.models.getHeroItemFromDTO
import com.chernybro.wb51.domain.models.HeroAttribute
import com.chernybro.wb51.domain.models.HeroDetailsItem
import com.chernybro.wb51.domain.models.HeroItem
import com.chernybro.wb51.presentation.models.ScreenState
import com.chernybro.wb51.utils.Constants
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

@OptIn(ExperimentalStdlibApi::class)
class HeroListApiImpl(private val okHttpClient: OkHttpClient, private val moshi: Moshi) :
    HeroListApi {

    override suspend fun getHeroes(): ScreenState<List<HeroItem>> {
        ScreenState.Loading(null)
        val request: Request = Request.Builder()
            .url(Constants.HEROES_BASE_URL + Constants.HEROES_STATS_ENDPOINT)
            .build()

        return try {
            val response = okHttpClient.newCall(request).execute()
            val json: String = response.body?.string() ?: ""

            val jsonAdapter: JsonAdapter<List<HeroStatsDTO>> = moshi.adapter()

            val heroesDTO = jsonAdapter.fromJson(json)
            val outputHeroesList = mutableListOf<HeroItem>()
            heroesDTO?.forEach { heroItemDTO ->
                outputHeroesList.add(heroItemDTO.getHeroItemFromDTO())
            }
            ScreenState.Success(data = outputHeroesList)
        } catch (io: IOException) {
            ScreenState.Error(error = R.string.error_io_exception)
        }
    }

    override suspend fun getHero(id: Int): ScreenState<HeroDetailsItem> {
        val request: Request = Request.Builder()
            .url(Constants.HEROES_BASE_URL + Constants.HEROES_STATS_ENDPOINT)
            .build()

        return try {
            val response = okHttpClient.newCall(request).execute()
            val json: String = response.body?.string() ?: ""

            val jsonAdapter: JsonAdapter<List<HeroStatsDTO>> = moshi.adapter()

            val heroesDTO = jsonAdapter.fromJson(json)
            val heroDetails = heroesDTO?.first { heroStatsDTO -> heroStatsDTO.id == id }

            val outputHero = heroDetails?.getHeroDetailsFromDTO()
            if (outputHero != null) {
                ScreenState.Success(data = outputHero)
            } else {
                ScreenState.Error(error = R.string.error_not_found)
            }
        } catch (io: IOException) {
            ScreenState.Error(error = R.string.error_io_exception)
        }
    }

}