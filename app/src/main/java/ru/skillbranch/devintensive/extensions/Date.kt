package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

// val Int.sec get() = this / 1000

fun Date.format(pattern: String="HH:mm:ss dd.MM.yy") : String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

/*
Временные интервалы для humanizeDiff
0с - 1с "только что"
1с - 45с "несколько секунд назад"
45с - 75с "минуту назад"
75с - 45мин "N минут назад"
45мин - 75мин "час назад"
75мин 22ч "N часов назад"
22ч - 26ч "день назад"
26ч - 360д "N дней назад"
>360д "более года назад"
*/

fun Date.humanizeDiff(date: Date = Date()): String {
	val seconds = abs(this.time - date.time) / 1000
    val minutes = 1 + (seconds - 16) / 60
    val hours = 1 + (minutes - 16) / 60
	return when(seconds) {
	    in 0..1 -> "только что"
	    in 1..45 -> "несколько секунд назад"
	    in 46..75 -> "минуту назад"
        in 75..195 -> "$minutes минуты назад"
        in 195..45*60 -> "$minutes минут назад"
        in 45*60 .. 75*60 -> "час назад"
        in 75*60 .. 4*60*60 -> "$hours часа назад"
        in 4*60*60 .. 22*60*60 -> "$hours часов назад"
        in 22*60*60 .. 26*60*60 -> "день назад"
        in 26*60*60 .. 360*24*60*60 -> "${ hours / 24 } дней назад"
	    else -> "более года назад"
	}
}

enum class TimeUnits(val size : Long) {
    SECOND(1000L) {
        override fun plural(value:Int) =
            "$value секунд${when(value % 10) {1 -> "а"; in 2..4 -> "ы"; else -> ""}}"
    },
    MINUTE(60000L) {
        override fun plural(value:Int) =
            "$value минут${when(value % 10) {1 -> "а"; in 2..4 -> "ы"; else -> ""}}"
    },
    HOUR(3600000L) {
        override fun plural(value:Int) =
            "$value ${when(value % 10) {1 -> "час"; in 2..4 -> "часа"; else -> "часов"}}"
    },
    DAY(86400000L) {
        override fun plural(value:Int) =
            "$value ${when(value % 10) {1 -> "день"; in 2..4 -> "дня"; else -> "дней"}}"
    };
    abstract fun plural(value:Int): String
}
