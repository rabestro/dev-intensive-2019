package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?>  {
        val parts : List<String>? = fullName?.split(" ")
        val firstName:String? = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return (if (firstName == "") null else firstName) to (if (lastName == "") null else lastName)
    }
}