package ru.skillbranch.devintensive.extensions

fun String.truncate(length : Int = 16) : String {
    return this.padEnd(length,'.')
}

fun String.stripHtml() : String {
    return ""
}