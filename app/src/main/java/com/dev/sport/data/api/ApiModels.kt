package com.dev.sport.data.api

import com.google.gson.annotations.SerializedName

// API DTOs

data class ApiGameDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("time") val time: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("starts_at") val startsAt: String?,
    @SerializedName("home") val home: ApiTeamDto?,
    @SerializedName("away") val away: ApiTeamDto?,
    @SerializedName("team1") val team1: ApiTeamDto?,
    @SerializedName("team2") val team2: ApiTeamDto?,
    @SerializedName("teams") val teams: List<ApiTeamDto>?,
    @SerializedName("league") val league: ApiLeagueDto?,
    @SerializedName("country") val country: ApiCountryDto?
)

data class ApiGameDetailsDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("time") val time: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("starts_at") val startsAt: String?,
    @SerializedName("home") val home: ApiTeamDto?,
    @SerializedName("away") val away: ApiTeamDto?,
    @SerializedName("team1") val team1: ApiTeamDto?,
    @SerializedName("team2") val team2: ApiTeamDto?,
    @SerializedName("teams") val teams: List<ApiTeamDto>?,
    @SerializedName("league") val league: ApiLeagueDto?,
    @SerializedName("country") val country: ApiCountryDto?,
    @SerializedName("status") val status: String?,
    @SerializedName("score") val score: ApiScoreDto?
)

data class ApiTeamDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("logo") val logo: String?,
    @SerializedName("icon") val icon: String?,
    @SerializedName("image") val image: String?
)

data class ApiLeagueDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("logo") val logo: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("country") val country: ApiCountryDto?
)

data class ApiCountryDto(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("flag") val flag: String?,
    @SerializedName("logo") val logo: String?,
    @SerializedName("image") val image: String?
)

data class ApiScoreDto(
    @SerializedName("home") val home: Int?,
    @SerializedName("away") val away: Int?
)
