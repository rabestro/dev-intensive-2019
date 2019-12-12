package ru.skillbranch.devintensive.models
import android.util.Log

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion() : String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    enum class Status(val color: Triple<Int, Int, Int>) {

        NORMAL(Triple(255,255,255)),
        WARNING(Triple(255,120,0)),
        DANGER(Triple(255,60,60)),
        CRITICAL(Triple(255,0,0));
        fun nextStatus() : Status = values()[
                if (this.ordinal < values().lastIndex) this.ordinal + 1 else 0 ]
    }

    fun listenAnswer(answer: String) : Pair<String, Triple<Int, Int, Int>> {
        if (question == Question.IDLE) return "${question.question}" to status.color

        var message = question.validateAnswer(answer)
        if (message.isEmpty()) {
            if (question.answers.contains(answer.toLowerCase())) {
                message = "Отлично - ты справился"
                question = question.nextQuestion()
            } else {
                status = status.nextStatus()
                if (status == Status.NORMAL) {
                    question = Question.NAME
                    message = "Это неправильный ответ. Давай все по новой"
                }
                else message = "Это неправильный ответ"
            }
        }
        return "${message}\n${question.question}" to status.color
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf<String>("бендер", "bender")) {
            
            override fun nextQuestion(): Question = PROFESSION
            
            override fun validateAnswer(answer: String): String =
                if (answer.isNotEmpty() && answer.first().isUpperCase()) ""
                else "Имя должно начинаться с заглавной буквы"
            },
                
        PROFESSION("Назови мою профессию?", listOf<String>("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validateAnswer(answer: String): String =
                if (answer.isNotEmpty() && answer.first().isLowerCase()) ""
                else "Профессия должна начинаться со строчной буквы"
        },
        MATERIAL("Из чего я сделан?", listOf<String>("метал", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validateAnswer(answer: String): String =
                if (!answer.contains(Regex("\\d"))) ""
                else "Материал не должен содержать цифр"
        },
        BDAY("Когда меня создали?", listOf<String>("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validateAnswer(answer: String): String =
                if (answer.contains(Regex("^\\d+$"))) ""
                else "Год моего рождения должен содержать только цифры"
        },
        SERIAL("Мой серийный номер?", listOf<String>("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validateAnswer(answer: String): String =
                if (answer.contains(Regex("^\\d{7}$"))) ""
                else "Серийный номер содержит только цифры, и их 7"
        },
        IDLE("На этом все, вопросов больше нет", listOf<String>()) {
            override fun nextQuestion(): Question = IDLE
            override fun validateAnswer(answer: String): String = ""
        };

        abstract fun nextQuestion() : Question
        abstract fun validateAnswer(answer: String) : String
    }
}