package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16) = if (this.length > length) this.substring(0, length).trimEnd() + "..." else this

fun String.stripHtml() : String {
    return ""
}
