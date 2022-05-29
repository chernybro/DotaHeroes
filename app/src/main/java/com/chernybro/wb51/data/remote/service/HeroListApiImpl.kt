package com.chernybro.wb51.data.remote.service

import com.chernybro.wb51.data.remote.models.HeroStatsDTO
import com.chernybro.wb51.domain.models.HeroAttribute
import com.chernybro.wb51.domain.models.HeroItem
import com.chernybro.wb51.domain.models.getFromDTO
import com.chernybro.wb51.presentation.models.ScreenState
import com.chernybro.wb51.utils.Constants
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class HeroListApiImpl(private val okHttpClient: OkHttpClient, private val moshi: Moshi) :
    HeroListApi {

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun getHeroes(): ScreenState<List<HeroItem>> {
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
                val attribute = when (heroItemDTO.primary_attr) {
                    "agi" -> HeroAttribute.Agility
                    "str" -> HeroAttribute.Strength
                    "int" -> HeroAttribute.Intelligence
                    else -> HeroAttribute.Unknown
                }
                outputHeroesList.add(heroItemDTO.getFromDTO(
                    primaryAttr = attribute,
                    Constants.HEROES_IMAGES_BASE + heroItemDTO.icon
                ))
            }
            ScreenState.Success(data = outputHeroesList)
        } catch (io: IOException) {
            ScreenState.Error(error = io.message.toString())
        }
    }

}