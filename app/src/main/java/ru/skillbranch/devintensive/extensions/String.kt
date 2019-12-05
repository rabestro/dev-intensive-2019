package ru.skillbranch.devintensive.extensions

fun String.truncate(length : Int = 16) : String {
    return this.padEnd(3,'.')
}