package util.extension

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun String.formatDate(): String {

    val localDateTime = this.toInstant().toLocalDateTime(TimeZone.currentSystemDefault())

    return buildString {
        append(localDateTime.date.dayOfMonth)
        append(" ")
        append(localDateTime.dayOfWeek.name.uppercaseFirst().take(3))
        append(", ")
        append(localDateTime.year)
    }
}

fun String.uppercaseFirst() = this.lowercase().replaceFirstChar { it.uppercase() }

infix fun String?.or(value: String): String = this ?: value
