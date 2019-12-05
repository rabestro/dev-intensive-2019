package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.extensions.toUserView
import ru.skillbranch.devintensive.models.*
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_instance() {
        val user1 = User("1")
        val user2 = User("2", "John", "Cena")
        val user3 = User("3", "John", "Silverhand", null, lastVisit = Date(), isOnline = true)

        user1.printMe()
        //user2.printMe()
        //user3.printMe()
        println("$user1 $user2 $user3")
    }

    @Test
    fun test_factory() {
        val user1 = User.makeUser("John Cena")
        val user2 = User.makeUser("John Wick")
        val user3 = User.makeUser("John Silverhand")

        user1.printMe()
        user2.printMe()
        user3.printMe()
        println("$user1 $user2 $user3")
    }

    @Test
    fun test_parseFullName() {
        val (firstName, lastName) = Utils.parseFullName(" John")
        println("Name = `$firstName` Surname = `$lastName`")

        assertEquals(null to null, Utils.parseFullName(null))
        assertEquals(null to null, Utils.parseFullName(""))
        assertEquals(null to null, Utils.parseFullName(" "))
        assertEquals("John" to null, Utils.parseFullName("John"))
    }

    @Test
    fun test_copy() {
        val user  = User.makeUser("John Wick")
        val user1 = user.copy(lastVisit = Date())
        val user2 = user.copy(lastVisit = Date().add(-2,TimeUnits.SECOND))
        val user3 = user.copy(lastName = "Silverhand",lastVisit = Date().add(2,TimeUnits.HOUR))

        user.printMe()
        user1.printMe()
        user2.printMe()
        user3.printMe()
        println("$user1 $user2 $user3")

    }
    
    @Test
    fun test_data_mapping() {
        val user  = User.makeUser("John Wick")
        val userView = user.toUserView()
        println(user)
        userView.printMe()
    }

    @Test
    fun test_toInitials() {
        var firstName = "john"
        var lastName = "snow"
        println("'$firstName $lastName` = `${Utils.toInitials(firstName, lastName)}`")

        assertEquals("JS", Utils.toInitials(firstName, lastName))
        assertEquals("J", Utils.toInitials(firstName, null))
        assertEquals("S", Utils.toInitials(null, lastName))
        assertEquals(null, Utils.toInitials(null, null))
        assertEquals(null, Utils.toInitials(" ", null))
        assertEquals(null, Utils.toInitials(" ", ""))
        assertEquals("JD", Utils.toInitials(" john", "Doe"))
    }
    
    @Test
    fun test_transliteration() {
        assertEquals("Zhenya Stereotipov", Utils.transliteration("Женя Стереотипов"))
        assertEquals("Amazing_Petr", Utils.transliteration("Amazing Петр","_"))
    }

    @Test
    fun test_abstract_factory() {
        val user  = User.makeUser("John Wick")
        val textMessage = BaseMessage.makeMessage(user, Chat(""), payload = "any text message", type = "text")
        val imgMessage = BaseMessage.makeMessage(user, Chat(""), payload = "any image url", type = "image")

        when(textMessage) {
            is TextMessage -> println("this is text message")
            is ImageMessage -> println("this is image message")
        }

        when(imgMessage) {
            is TextMessage -> println("this is text message")
            is ImageMessage -> println("this is image message")
        }

        println(textMessage.formatMessage())
    }

    @Test
    fun test_String_truncate() {
        val text = "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»"
    }
}
