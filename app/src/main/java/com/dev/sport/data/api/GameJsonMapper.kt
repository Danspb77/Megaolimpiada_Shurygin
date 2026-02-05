package com.dev.sport.data.api

import com.dev.sport.domain.model.StockEvent
import com.dev.sport.domain.model.StockEventDetails
import com.dev.sport.util.DateTimeFormatters
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

object GameJsonMapper {
    fun toStockEvent(item: JsonObject): StockEvent? {
        val id = item.readLong("id", "Id", "game_id", "GameId") ?: return null
        val time = item.readString(
            "time", "Time", "date", "Date", "starts_at", "startsAt", "StartTime", "Start", "StartDate"
        )
        val (home, away) = item.readTeams()
        val seasonObj = item.readObject("season", "Season")
        val leagueObj = item.readObject("league", "League", "tournament", "Tournament", "competition", "Competition")
            ?: seasonObj?.readObject("league", "League")
        val leagueName = leagueObj?.readString("name", "Name", "league_name", "LeagueName")
            ?: item.readString("league_name", "LeagueName")
            ?: "Unknown league"
        val leagueLogo = leagueObj?.readString("logo", "Logo", "image", "Image", "icon", "Icon")
        val countryObj = item.readObject("country", "Country")
            ?: leagueObj?.readObject("country", "Country")
            ?: seasonObj?.readObject("league", "League")?.readObject("country", "Country")
            ?: item.readObject("homeTeam", "HomeTeam")?.readObject("country", "Country")
        val countryName = countryObj?.readString("name", "Name", "country_name", "CountryName")
            ?: item.readString("country_name", "CountryName")
            ?: "Unknown country"
        val countryFlag = countryObj?.readString("flag", "Flag", "logo", "Logo", "image", "Image")

        return StockEvent(
            id = id,
            startTime = DateTimeFormatters.formatUi(time),
            issuerA = home?.name ?: "TBD",
            issuerB = away?.name ?: "TBD",
            exchangeName = leagueName,
            countryName = countryName,
            issuerALogo = home?.logo,
            issuerBLogo = away?.logo,
            exchangeLogo = leagueLogo,
            countryFlag = countryFlag
        )
    }

    fun toStockEventDetails(item: JsonObject): StockEventDetails? {
        val base = toStockEvent(item) ?: return null
        val status = item.readString("status", "Status", "state", "State")
        val scoreObj = item.readObject("score", "Score")
        val scoreText = if (scoreObj != null) {
            val homeScore = scoreObj.readString("home", "Home", "team1", "Team1", "localteam", "LocalTeam")
            val awayScore = scoreObj.readString("away", "Away", "team2", "Team2", "visitorteam", "VisitorTeam")
            if (homeScore != null && awayScore != null) "${homeScore}:${awayScore}" else null
        } else {
            item.readString("score", "Score")
        }
        return StockEventDetails(
            id = base.id,
            startTime = base.startTime,
            issuerA = base.issuerA,
            issuerB = base.issuerB,
            exchangeName = base.exchangeName,
            countryName = base.countryName,
            issuerALogo = base.issuerALogo,
            issuerBLogo = base.issuerBLogo,
            exchangeLogo = base.exchangeLogo,
            countryFlag = base.countryFlag,
            status = status,
            scoreText = scoreText
        )
    }

    private data class TeamInfo(val name: String?, val logo: String?)

    private fun JsonObject.readTeams(): Pair<TeamInfo?, TeamInfo?> {
        val home = readTeam(
            "home", "Home", "homeTeam", "HomeTeam", "team1", "Team1", "localteam", "LocalTeam", "home_team"
        )
        val away = readTeam(
            "away", "Away", "awayTeam", "AwayTeam", "team2", "Team2", "visitorteam", "VisitorTeam", "away_team"
        )
        if (home != null || away != null) return home to away

        val teams = readElement("teams", "Teams")
        if (teams is JsonArray && teams.size() >= 2) {
            val h = teams[0]?.asJsonObject?.toTeamInfo()
            val a = teams[1]?.asJsonObject?.toTeamInfo()
            return h to a
        }
        if (teams is JsonObject) {
            val h = teams.readTeam("home", "Home")
            val a = teams.readTeam("away", "Away")
            return h to a
        }
        val homeName = readString("home_name", "HomeName", "HomeTeamName")
        val awayName = readString("away_name", "AwayName", "AwayTeamName")
        return TeamInfo(homeName, null) to TeamInfo(awayName, null)
    }

    private fun JsonObject.readTeam(vararg keys: String): TeamInfo? {
        for (key in keys) {
            val element = readElement(key) ?: continue
            if (element is JsonObject) {
                return element.toTeamInfo()
            }
            if (element.isJsonPrimitive && element.asJsonPrimitive.isString) {
                return TeamInfo(element.asString, null)
            }
        }
        return null
    }

    private fun JsonObject.toTeamInfo(): TeamInfo {
        val name = readString("name", "Name", "team_name", "TeamName", "short_name", "ShortName")
        val logo = readString("logo", "Logo", "icon", "Icon", "image", "Image", "flag", "Flag")
        return TeamInfo(name, logo)
    }

    private fun JsonObject.readString(vararg keys: String): String? {
        for (key in keys) {
            val element = readElement(key) ?: continue
            if (element.isJsonPrimitive) {
                return element.asString
            }
        }
        return null
    }

    private fun JsonObject.readLong(vararg keys: String): Long? {
        for (key in keys) {
            val element = readElement(key) ?: continue
            if (element.isJsonPrimitive) {
                return element.asLong
            }
        }
        return null
    }

    private fun JsonObject.readObject(vararg keys: String): JsonObject? {
        for (key in keys) {
            val element = readElement(key) ?: continue
            if (element is JsonObject) return element
        }
        return null
    }

    private fun JsonObject.readElement(vararg keys: String): JsonElement? {
        for (key in keys) {
            val direct = get(key)
            if (direct != null) return direct
            val match = entrySet().firstOrNull { it.key.equals(key, ignoreCase = true) }
            if (match != null) return match.value
        }
        return null
    }
}
