package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

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
val Int.sec get() = this * TimeUnits.SECOND.size
val Int.min get() = this * TimeUnits.MINUTE.size
val Int.hour get() = this * TimeUnits.HOUR.size
val Long.day get() = this * TimeUnits.DAY.size
val Long.asMin get() = this.absoluteValue / TimeUnits.MINUTE.size
val Long.asHour get() = this.absoluteValue / TimeUnits.HOUR.size
val Long.asDay get() = this.absoluteValue / TimeUnits.DAY.size

fun Date.humanizeDiff(date: Date = Date()) =
    when(val diff: Long = date.time - time) {
	    in 0..1.sec -> "только что"
	    in 1.sec..45.sec -> "несколько секунд назад"
	    in 45.sec..75.sec -> "минуту назад"
        in 75.sec..45.min -> "${TimeUnits.MINUTE.plural(diff.asMin)} назад"
        in 45.min..75.min-> "час назад"
        in 75.min .. 22.hour -> "${TimeUnits.HOUR.plural(diff.asHour)} назад"
        in 22.hour .. 26.hour -> "день назад"
        in 27.hour..360L.day -> "${TimeUnits.DAY.plural(diff.asDay)} назад"
        in (-1).sec..0.sec -> "прямо сейчас"
        in (-45).sec..(-1).sec -> "через несколько секунд"
        in (-75).sec..(-45).sec -> "через минуту"
        in (-45).min..(-75).sec -> "через ${TimeUnits.MINUTE.plural(diff.asMin)}"
        in (-75).min..(-45).min -> "через час"
        in (-22).hour..(-75).min -> "через ${TimeUnits.HOUR.plural(diff.asHour)}"
        in (-26).hour..(-22).hour -> "через день"
        in (-360L).day..(-26).hour -> "через ${TimeUnits.DAY.plural(diff.asDay)}"
	    else -> if (diff > 0) "более года назад" else "более чем через год"
	}

enum class TimeUnits(val size : Long, val russianName: Array<String>) {

    SECOND(1000L, arrayOf("секунд", "секунду", "секунды")),

    MINUTE(60000L, arrayOf("минут", "минуту", "минуты")),

    HOUR(3600000L, arrayOf("часов", "час", "часа")),

    DAY(86400000L, arrayOf("дней", "день", "дня"));

    fun plural(value: Long): String = "$value ${this.russianName[calculateEnding(value)]}"

    private fun calculateEnding(value: Long) = when {
        value % 100 in 5..20 -> 0
        value % 10 in 2..4 -> 2
        value % 10 == 1L -> 1
        else -> 0
    }
}
