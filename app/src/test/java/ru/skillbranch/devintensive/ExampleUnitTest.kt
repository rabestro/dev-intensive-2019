package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.models.User
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
}
