package com.chernybro.wb51.domain.models

import com.chernybro.wb51.data.remote.models.HeroStatsDTO

data class HeroItem(
    val id: Int,
    val name: String,
    val avatar: String? = null,
    val primaryAttr: HeroAttribute
)

enum class HeroAttribute {
    Intelligence, Agility, Strength, Unknown
}

