package ru.skillbranch.devintensive.models
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User (
    val id:String,
    var firstName:String?,
    var lastName:String?,
    var avatar:String?,
    var rating:Int = 0,
    var respect:Int = 0,
    var lastVisit:Date? = null,
    var isOnline:Boolean = false
    )
{
    var introBit:String

    constructor(id:String, firstName:String?, lastName:String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id:String) : this(id, "John", "Doe")

    init {
        introBit = getIntro()
        println("It's Alive!\n${
            if (lastName === "Doe") "His name is $firstName" 
            else "And his name is $firstName!"}\n ${getIntro()}")
    }

    companion object Factory {
        private var lastId:Int = -1
        fun makeUser(fullName:String?) : User {
            ++lastId
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(
                id = "$lastId",
                firstName = firstName,
                lastName = lastName)
        }
    }

    private fun getIntro() = """
        Full name is $firstName $lastName
    """.trimIndent()


    fun printMe() = println("""
            id $id:
            firstName: $firstName
            lastName: $lastName
            avatar: $avatar
            rating: $rating
            respect: $respect            
            lastVisit: $lastVisit            
            isOnline: $isOnline            
         """.trimIndent())
}