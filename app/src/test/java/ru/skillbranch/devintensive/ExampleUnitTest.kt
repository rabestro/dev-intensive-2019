package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.extensions.humanizeDiff
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

    @Test
    fun test_Date_humanizeDiff() {

        assertEquals("2 часа назад", Date().add(-2, TimeUnits.HOUR).humanizeDiff())
        assertEquals("5 дней назад", Date().add(-5, TimeUnits.DAY).humanizeDiff() )
        assertEquals("через 2 минуты", Date().add(2, TimeUnits.MINUTE).humanizeDiff())
        assertEquals("через 7 дней", Date().add(7, TimeUnits.DAY).humanizeDiff())
        assertEquals("более года назад", Date().add(-400, TimeUnits.DAY).humanizeDiff())
        assertEquals("более чем через год", Date().add(400, TimeUnits.DAY).humanizeDiff())
    }

    @Test
    fun test_plural() {
        assertEquals("0 секунд", TimeUnits.SECOND.plural(0))
        assertEquals("1 секунду", TimeUnits.SECOND.plural(1))
        assertEquals("2 секунды", TimeUnits.SECOND.plural(2))
        assertEquals("7 секунд", TimeUnits.SECOND.plural(7))
        assertEquals("14 секунд", TimeUnits.SECOND.plural(14))
        assertEquals("24 секунды", TimeUnits.SECOND.plural(24))
        assertEquals("102 секунды", TimeUnits.SECOND.plural(102))
        assertEquals("112 секунд", TimeUnits.SECOND.plural(112))
        assertEquals("122 секунды", TimeUnits.SECOND.plural(122))
        assertEquals("311 секунд", TimeUnits.SECOND.plural(311))

        assertEquals("0 минут", TimeUnits.MINUTE.plural(0))
        assertEquals("1 минуту", TimeUnits.MINUTE.plural(1))
        assertEquals("2 минуты", TimeUnits.MINUTE.plural(2))
        assertEquals("7 минут", TimeUnits.MINUTE.plural(7))
        assertEquals("14 минут", TimeUnits.MINUTE.plural(14))
        assertEquals("24 минуты", TimeUnits.MINUTE.plural(24))
        assertEquals("102 минуты", TimeUnits.MINUTE.plural(102))
        assertEquals("112 минут", TimeUnits.MINUTE.plural(112))
        assertEquals("122 минуты", TimeUnits.MINUTE.plural(122))
        assertEquals("311 минут", TimeUnits.MINUTE.plural(311))

        assertEquals("0 часов", TimeUnits.HOUR.plural(0))
        assertEquals("1 час", TimeUnits.HOUR.plural(1))
        assertEquals("2 часа", TimeUnits.HOUR.plural(2))
        assertEquals("7 часов", TimeUnits.HOUR.plural(7))
        assertEquals("14 часов", TimeUnits.HOUR.plural(14))
        assertEquals("24 часа", TimeUnits.HOUR.plural(24))
        assertEquals("102 часа", TimeUnits.HOUR.plural(102))
        assertEquals("112 часов", TimeUnits.HOUR.plural(112))
        assertEquals("122 часа", TimeUnits.HOUR.plural(122))
        assertEquals("311 часов", TimeUnits.HOUR.plural(311))

        assertEquals("0 дней", TimeUnits.DAY.plural(0))
        assertEquals("1 день", TimeUnits.DAY.plural(1))
        assertEquals("2 дня", TimeUnits.DAY.plural(2))
        assertEquals("7 дней", TimeUnits.DAY.plural(7))
        assertEquals("14 дней", TimeUnits.DAY.plural(14))
        assertEquals("24 дня", TimeUnits.DAY.plural(24))
        assertEquals("102 дня", TimeUnits.DAY.plural(102))
        assertEquals("112 дней", TimeUnits.DAY.plural(112))
        assertEquals("122 дня", TimeUnits.DAY.plural(122))
        assertEquals("311 дней", TimeUnits.DAY.plural(311))
        assertEquals("1234 дня", TimeUnits.DAY.plural(1234))
    }

    @Test
    fun test_time_prular() {

        println(TimeUnits.DAY.plural(0))
    }

}
