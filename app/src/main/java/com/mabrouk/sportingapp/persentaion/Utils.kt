package com.mabrouk.sportingapp.persentaion

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mabrouk.sportingapp.domain.models.Event
import java.io.InputStreamReader


/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/25/23
 */

abstract class JsonNavType<T> : NavType<T>(isNullableAllowed = false) {
    abstract fun fromJsonParse(value: String): T
    abstract fun T.getJsonParse(): String

    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { parseValue(it) }

    override fun parseValue(value: String): T = fromJsonParse(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, value.getJsonParse())
    }
}

private const val HEX_CHARS = "0123456789ABCDEF"
fun hexStringToByteArray(data: String): ByteArray {

    val result = ByteArray(data.length / 2)

    for (i in data.indices step 2) {
        val firstIndex = HEX_CHARS.indexOf(data[i]);
        val secondIndex = HEX_CHARS.indexOf(data[i + 1]);

        val octet = firstIndex.shl(4).or(secondIndex)
        result[i.shr(1)] = octet.toByte()
    }

    return result
}

private val HEX_CHARS_ARRAY = "0123456789ABCDEF".toCharArray()
fun toHex(byteArray: ByteArray): String {
    val result = StringBuffer()

    byteArray.forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS_ARRAY[firstIndex])
        result.append(HEX_CHARS_ARRAY[secondIndex])
    }

    return result.toString()
}

fun Event.toNewObject() =
    Event(
        event_key,
        event_date,
        event_time,
        event_home_team,
        home_team_key,
        event_away_team,
        away_team_key,
        event_halftime_result,
        event_final_result,
        event_ft_result,
        event_penalty_result,
        event_status,
        country_name,
        league_name,
        league_key,
        league_round,
        league_season,
        event_live,
        event_stadium,
        event_referee,
        home_team_logo,
        away_team_logo,
        event_country_key,
        league_logo,
        country_logo,
        event_home_formation,
        event_away_formation,
        fk_stage_key,
        stage_name,
        goalscorers,
        cards,
        lineups,
        statistics,
        isHeader
    )

val allFormations = listOf(
    "4-2-3-1", "4-3-1-2", "4-3-3", "4-2-4", "3-4-3", "3-5-2", "2-3-5", "1-2-7",
    "4-5-1", "5-3-2", "5-2-3", "5-4-1", "4-4-1-1", "5-2-1-2", "4-1-4-1", "4-4-2", "4-3-2-1"
)

inline fun <reified T : Any> parseToObject(value: String?): T? {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(value, type)
}


val data = """
    {
    "event_key": 1059491,
    "event_date": "2023-02-24",
    "event_time": "21:00",
    "event_home_team": "Fulham",
    "home_team_key": 3085,
    "event_away_team": "Wolves",
    "away_team_key": 3077,
    "event_halftime_result": "0 - 1",
    "event_final_result": "1 - 1",
    "event_ft_result": "1 - 1",
    "event_penalty_result": "",
    "event_status": "Finished",
    "country_name": "England",
    "league_name": "Premier League",
    "league_key": 152,
    "league_round": "Round 25",
    "league_season": "2022/2023",
    "event_live": "1",
    "event_stadium": "Craven Cottage (London)",
    "event_referee": "M. Oliver",
    "home_team_logo": "https://apiv2.allsportsapi.com/logo/3085_fulham.jpg",
    "away_team_logo": "https://apiv2.allsportsapi.com/logo/3077_wolverhampton-wanderers.jpg",
    "event_country_key": 44,
    "league_logo": "https://apiv2.allsportsapi.com/logo/logo_leagues/152_premier-league.png",
    "country_logo": "https://apiv2.allsportsapi.com/logo/logo_country/44_england.png",
    "event_home_formation": "4-2-3-1",
    "event_away_formation": "4-4-2",
    "fk_stage_key": 6,
    "stage_name": "Current",
    "league_group": null,
    "goalscorers": [
    {
        "time": "23",
        "home_scorer": "",
        "home_scorer_id": "",
        "home_assist": "",
        "home_assist_id": "",
        "score": "0 - 1",
        "away_scorer": "P. Sarabia",
        "away_scorer_id": "785997195",
        "away_assist": "R. Jimenez",
        "away_assist_id": "1249672088",
        "info": "",
        "info_time": "1st Half"
    },
    {
        "time": "64",
        "home_scorer": "M. Solomon",
        "home_scorer_id": "2093493573",
        "home_assist": "A. Robinson",
        "home_assist_id": "2401894866",
        "score": "1 - 1",
        "away_scorer": "",
        "away_scorer_id": "",
        "away_assist": "",
        "away_assist_id": "",
        "info": "",
        "info_time": "2nd Half"
    }
    ],
    "substitutes": [
    {
        "time": "60",
        "home_scorer": [],
        "home_assist": null,
        "score": "substitution",
        "away_scorer": {
        "in": "Adama Traoré",
        "out": "Matheus Cunha",
        "in_id": 3535917074,
        "out_id": 3425600851
    },
        "away_assist": null,
        "info": null,
        "info_time": "2nd Half"
    },
    {
        "time": "76",
        "home_scorer": [],
        "home_assist": null,
        "score": "substitution",
        "away_scorer": {
        "in": "Daniel Podence",
        "out": "Matheus Nunes",
        "in_id": 266136704,
        "out_id": 360186901
    },
        "away_assist": null,
        "info": null,
        "info_time": "2nd Half"
    },
    {
        "time": "84",
        "home_scorer": [],
        "home_assist": null,
        "score": "substitution",
        "away_scorer": {
        "in": "Diego Costa",
        "out": "R. Jiménez",
        "in_id": 459839934,
        "out_id": 1249672088
    },
        "away_assist": null,
        "info": null,
        "info_time": "2nd Half"
    },
    {
        "time": "76",
        "home_scorer": [],
        "home_assist": null,
        "score": "substitution",
        "away_scorer": {
        "in": "João Moutinho",
        "out": "Pablo Sarabia",
        "in_id": 483854977,
        "out_id": 785997195
    },
        "away_assist": null,
        "info": null,
        "info_time": "2nd Half"
    },
    {
        "time": "83",
        "home_scorer": {
        "in": "H. Wilson",
        "out": "Willian",
        "in_id": 1384836880,
        "out_id": 2078494507
    },
        "home_assist": null,
        "score": "substitution",
        "away_scorer": [],
        "away_assist": null,
        "info": null,
        "info_time": "2nd Half"
    },
    {
        "time": "46",
        "home_scorer": {
        "in": "M. Solomon",
        "out": "B. De Cordova-Reid",
        "in_id": 2093493573,
        "out_id": 3402366216
    },
        "home_assist": null,
        "score": "substitution",
        "away_scorer": [],
        "away_assist": null,
        "info": null,
        "info_time": "2nd Half"
    },
    {
        "time": "46",
        "home_scorer": {
        "in": "S. Lukić",
        "out": "H. Reed",
        "in_id": 2759686506,
        "out_id": 2627782501
    },
        "home_assist": null,
        "score": "substitution",
        "away_scorer": [],
        "away_assist": null,
        "info": null,
        "info_time": "2nd Half"
    }
    ],
    "cards": [
    {
        "time": "19",
        "home_fault": "J. Palhinha",
        "card": "yellow card",
        "away_fault": "",
        "info": "",
        "home_player_id": "3839162053",
        "away_player_id": "",
        "info_time": "1st Half"
    },
    {
        "time": "72",
        "home_fault": "A. Pereira",
        "card": "yellow card",
        "away_fault": "",
        "info": "",
        "home_player_id": "3976372056",
        "away_player_id": "",
        "info_time": "2nd Half"
    }
    ],
    "lineups": {
    "home_team": {
    "starting_lineups": [
    {
        "player": "Andreas Pereira",
        "player_number": 18,
        "player_position": 9,
        "player_country": null,
        "player_key": 3976372056,
        "info_time": ""
    },
    {
        "player": "Antonee Robinson",
        "player_number": 33,
        "player_position": 5,
        "player_country": null,
        "player_key": 2401894866,
        "info_time": ""
    },
    {
        "player": "Bernd Leno",
        "player_number": 17,
        "player_position": 1,
        "player_country": null,
        "player_key": 3975994970,
        "info_time": ""
    },
    {
        "player": "Bobby Decordova-Reid",
        "player_number": 14,
        "player_position": 8,
        "player_country": null,
        "player_key": 3402366216,
        "info_time": ""
    },
    {
        "player": "Carlos Vinícius",
        "player_number": 30,
        "player_position": 11,
        "player_country": null,
        "player_key": 85026834,
        "info_time": ""
    },
    {
        "player": "Harrison Reed",
        "player_number": 6,
        "player_position": 6,
        "player_country": null,
        "player_key": 2627782501,
        "info_time": ""
    },
    {
        "player": "Issa Diop",
        "player_number": 31,
        "player_position": 3,
        "player_country": null,
        "player_key": 1574510501,
        "info_time": ""
    },
    {
        "player": "João Palhinha",
        "player_number": 26,
        "player_position": 7,
        "player_country": null,
        "player_key": 3839162053,
        "info_time": ""
    },
    {
        "player": "Kenny Tete",
        "player_number": 2,
        "player_position": 2,
        "player_country": null,
        "player_key": 4126465973,
        "info_time": ""
    },
    {
        "player": "Tim Ream",
        "player_number": 13,
        "player_position": 4,
        "player_country": null,
        "player_key": 432989996,
        "info_time": ""
    },
    {
        "player": "Willian",
        "player_number": 20,
        "player_position": 10,
        "player_country": null,
        "player_key": 2078494507,
        "info_time": ""
    }
    ],
    "substitutes": [
    {
        "player": "Cédric Soares",
        "player_number": 12,
        "player_position": 0,
        "player_country": null,
        "player_key": 3376420635,
        "info_time": ""
    },
    {
        "player": "Daniel James",
        "player_number": 21,
        "player_position": 0,
        "player_country": null,
        "player_key": 3695380441,
        "info_time": ""
    },
    {
        "player": "Harry Wilson",
        "player_number": 8,
        "player_position": 0,
        "player_country": null,
        "player_key": 1384836880,
        "info_time": ""
    },
    {
        "player": "Layvin Kurzawa",
        "player_number": 3,
        "player_position": 0,
        "player_country": null,
        "player_key": 2894915271,
        "info_time": ""
    },
    {
        "player": "Manor Solomon",
        "player_number": 11,
        "player_position": 0,
        "player_country": null,
        "player_key": 2093493573,
        "info_time": ""
    },
    {
        "player": "Marek Rodák",
        "player_number": 1,
        "player_position": 0,
        "player_country": null,
        "player_key": 1723023560,
        "info_time": ""
    },
    {
        "player": "Sasa Lukic",
        "player_number": 28,
        "player_position": 0,
        "player_country": null,
        "player_key": 2759686506,
        "info_time": ""
    },
    {
        "player": "Shane Duffy",
        "player_number": 5,
        "player_position": 0,
        "player_country": null,
        "player_key": 2255169837,
        "info_time": ""
    },
    {
        "player": "Tosin Adarabioyo",
        "player_number": 4,
        "player_position": 0,
        "player_country": null,
        "player_key": 9676216,
        "info_time": ""
    }
    ],
    "coaches": [
    {
        "coache": "Marco Silva",
        "coache_country": null
    }
    ],
    "missing_players": []
},
    "away_team": {
    "starting_lineups": [
    {
        "player": "Craig Dawson",
        "player_number": 15,
        "player_position": 3,
        "player_country": null,
        "player_key": 281941140,
        "info_time": ""
    },
    {
        "player": "Hugo Bueno",
        "player_number": 64,
        "player_position": 5,
        "player_country": null,
        "player_key": 1311489551,
        "info_time": ""
    },
    {
        "player": "José Sá",
        "player_number": 1,
        "player_position": 1,
        "player_country": null,
        "player_key": 1177906827,
        "info_time": ""
    },
    {
        "player": "Mario Lemina",
        "player_number": 5,
        "player_position": 8,
        "player_country": null,
        "player_key": 870571787,
        "info_time": ""
    },
    {
        "player": "Matheus Cunha",
        "player_number": 12,
        "player_position": 11,
        "player_country": null,
        "player_key": 3425600851,
        "info_time": ""
    },
    {
        "player": "Matheus Nunes",
        "player_number": 27,
        "player_position": 9,
        "player_country": null,
        "player_key": 360186901,
        "info_time": ""
    },
    {
        "player": "Max Kilman",
        "player_number": 23,
        "player_position": 4,
        "player_country": null,
        "player_key": 2655737547,
        "info_time": ""
    },
    {
        "player": "Nélson Semedo",
        "player_number": 22,
        "player_position": 2,
        "player_country": null,
        "player_key": 4232280765,
        "info_time": ""
    },
    {
        "player": "Pablo Sarabia",
        "player_number": 21,
        "player_position": 6,
        "player_country": null,
        "player_key": 785997195,
        "info_time": ""
    },
    {
        "player": "Raúl Jiménez",
        "player_number": 9,
        "player_position": 10,
        "player_country": null,
        "player_key": 1249672088,
        "info_time": ""
    },
    {
        "player": "Rúben Neves",
        "player_number": 8,
        "player_position": 7,
        "player_country": null,
        "player_key": 2307621631,
        "info_time": ""
    }
    ],
    "substitutes": [
    {
        "player": "Adama Traoré",
        "player_number": 37,
        "player_position": 0,
        "player_country": null,
        "player_key": 3535917074,
        "info_time": ""
    },
    {
        "player": "Daniel Bentley",
        "player_number": 25,
        "player_position": 0,
        "player_country": null,
        "player_key": 2378153847,
        "info_time": ""
    },
    {
        "player": "Daniel Podence",
        "player_number": 10,
        "player_position": 0,
        "player_country": null,
        "player_key": 266136704,
        "info_time": ""
    },
    {
        "player": "Diego Costa",
        "player_number": 29,
        "player_position": 0,
        "player_country": null,
        "player_key": 459839934,
        "info_time": ""
    },
    {
        "player": "João Gomes",
        "player_number": 35,
        "player_position": 0,
        "player_country": null,
        "player_key": 3279900432,
        "info_time": ""
    },
    {
        "player": "João Moutinho",
        "player_number": 28,
        "player_position": 0,
        "player_country": null,
        "player_key": 483854977,
        "info_time": ""
    },
    {
        "player": "Jonny Otto",
        "player_number": 19,
        "player_position": 0,
        "player_country": null,
        "player_key": 3725503086,
        "info_time": ""
    },
    {
        "player": "Nathan Collins",
        "player_number": 4,
        "player_position": 0,
        "player_country": null,
        "player_key": 2572514283,
        "info_time": ""
    },
    {
        "player": "Rayan Aït Nouri",
        "player_number": 3,
        "player_position": 0,
        "player_country": null,
        "player_key": 1173416628,
        "info_time": ""
    }
    ],
    "coaches": [
    {
        "coache": "Lopetegui",
        "coache_country": null
    }
    ],
    "missing_players": []
}
},
    "statistics": [
    {
        "type": "Throw In",
        "home": "34",
        "away": "22"
    },
    {
        "type": "Free Kick",
        "home": "17",
        "away": "14"
    },
    {
        "type": "Goal Kick",
        "home": "10",
        "away": "8"
    },
    {
        "type": "Substitution",
        "home": "3",
        "away": "4"
    },
    {
        "type": "Attacks",
        "home": "99",
        "away": "115"
    },
    {
        "type": "Dangerous Attacks",
        "home": "47",
        "away": "36"
    },
    {
        "type": "On Target",
        "home": "5",
        "away": "2"
    },
    {
        "type": "Off Target",
        "home": "5",
        "away": "6"
    },
    {
        "type": "Shots Total",
        "home": "10",
        "away": "8"
    },
    {
        "type": "Shots On Goal",
        "home": "5",
        "away": "2"
    },
    {
        "type": "Shots Off Goal",
        "home": "2",
        "away": "5"
    },
    {
        "type": "Shots Blocked",
        "home": "3",
        "away": "1"
    },
    {
        "type": "Shots Inside Box",
        "home": "3",
        "away": "6"
    },
    {
        "type": "Shots Outside Box",
        "home": "7",
        "away": "2"
    },
    {
        "type": "Fouls",
        "home": "12",
        "away": "17"
    },
    {
        "type": "Corners",
        "home": "3",
        "away": "3"
    },
    {
        "type": "Offsides",
        "home": "1",
        "away": "1"
    },
    {
        "type": "Ball Possession",
        "home": "50%",
        "away": "50%"
    },
    {
        "type": "Yellow Cards",
        "home": "2",
        "away": "0"
    },
    {
        "type": "Saves",
        "home": "1",
        "away": "4"
    },
    {
        "type": "Passes Total",
        "home": "435",
        "away": "437"
    },
    {
        "type": "Passes Accurate",
        "home": "353",
        "away": "334"
    }
    ]
}
"""

val getEvent = Gson().fromJson<Event>(data, Event::class.java)
