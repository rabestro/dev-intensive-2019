package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion() : String = when (question) {
        Question.NAME -> Question.NAME.name
        Question.PROFESSION -> Question.PROFESSION.name
        Question.MATERIAL -> Question.MATERIAL.name
        Question.BDAY -> Question.BDAY.name
        Question.SERIAL -> Question.SERIAL.name
        Question.IDLE -> Question.IDLE.name
    }

    fun listenAnswer(answer: String) : Pair<String, Triple<Int, Int, Int>> =
        if (question.answers.contains(answer)) {
            "Олично! Это правильный ответ!" to status.color
        } else {
            status = status.nextStatus()
            "Это не правльный ответ!" to status.color
        }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        ERROR(Triple(255,60,60)),
        CRITICAL(Triple(255,255,0));

        fun nextStatus() : Status = values()[
                if (this.ordinal < values().lastIndex) this.ordinal + 1 else 0 ]
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf<String>("бендер", "bender")),
        PROFESSION("Назови мою профессию?", listOf<String>("сгибальщик", "bender")),
        MATERIAL("Из чего я сделан?", listOf<String>("метал", "дерево", "metal", "iron", "wood")),
        BDAY("Когда меня создали?", listOf<String>("2993")),
        SERIAL("Мой серийный номер?", listOf<String>("2716057")),
        IDLE("На этом всё, вопросов больше нет.", listOf<String>()),
    }
}