package com.chernybro.wb51.data.remote.models

import com.chernybro.wb51.domain.models.*
import com.chernybro.wb51.utils.Constants
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeroStatsDTO(
    val agi_gain: Double,
    val attack_range: Int,
    val attack_rate: Double,
    val attack_type: String,
    val base_agi: Int,
    val base_armor: Double,
    val base_attack_max: Int,
    val base_attack_min: Int,
    val base_health: Int,
    val base_health_regen: Double,
    val base_int: Int,
    val base_mana: Int,
    val base_mana_regen: Double,
    val base_mr: Int,
    val base_str: Int,
    val hero_id: Int,
    val icon: String,
    val id: Int,
    val img: String,
    val int_gain: Double,
    val localized_name: String,
    val move_speed: Int,
    val primary_attr: String,
    val str_gain: Double
)

fun HeroStatsDTO.getHeroItemFromDTO(): HeroItem {
    val attribute = when (this.primary_attr) {
        "agi" -> HeroAttribute.Agility
        "str" -> HeroAttribute.Strength
        "int" -> HeroAttribute.Intelligence
        else -> HeroAttribute.Unknown
    }
    return HeroItem(
        id = id,
        name = localized_name,
        primaryAttr = attribute,
        avatar = Constants.HEROES_IMAGES_BASE + this.icon.substring(1)
    )
}

fun HeroStatsDTO.getHeroDetailsFromDTO(): HeroDetailsItem {
    return HeroDetailsItem(
        id = id,
        name = localized_name,
        imageUrl = Constants.HEROES_IMAGES_BASE + this.img.substring(1),
        mana = HeroMana(
            baseValue = base_mana,
            regen = base_mana_regen
        ),
        health = HeroHealth(
            baseValue = base_health,
            regen = base_health_regen
        ),
        stats = HeroStats(
            attributes = HeroAttributes(
                agility = HeroAgility(
                    gain = agi_gain,
                    base = base_agi
                ),
                intelligence = HeroIntelligence(
                    gain = int_gain,
                    base = base_int
                ),
                strength = HeroStrength(
                    gain = str_gain,
                    base = base_str
                )
            ),
            mobility = HeroMobility(
                speed = move_speed
            ),
            attack = HeroAttack(
                min = base_attack_min,
                max = base_attack_max,
                rate = attack_rate,
                range = attack_range,
                type = attack_type
            ),
            defence = HeroDefence(
                magicResistant = base_mr,
                armor = base_armor
            )
        )
    )
}