package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?, String?>  {
        val parts : List<String>? = fullName?.split(" ")
        val firstName:String? = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return (if (firstName == "") null else firstName) to (if (lastName == "") null else lastName)
    }

    fun transliteration(payload: String, devider:String = " "): String {
        return TODO("Not implemented")
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val a :Char? = firstName?.trimStart()?.firstOrNull()?.toUpperCase()
        val b :Char?  = lastName?.trimStart()?.firstOrNull()?.toUpperCase()

        return if (a == null && b == null) null
            else if (a==null) b.toString()
            else if (b==null) a.toString()
            else a.toString() + b
    }
}