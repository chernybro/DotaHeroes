package com.chernybro.wb51.data.remote.models

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