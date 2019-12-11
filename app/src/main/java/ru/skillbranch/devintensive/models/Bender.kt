package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {
    var incorrectAnswerSequence = 0

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
        ERROR(Triple(255,60,60)),
        CRITICAL(Triple(255,255,0));
        fun nextStatus() : Status = values()[
                if (this.ordinal < values().lastIndex) this.ordinal + 1 else 0 ]
    }

/*

    Валидация

    Question.NAME -> "Имя должно начинаться с заглавной буквы"
    Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
    Question.MATERIAL -> "Материал не должен содержать цифр"
    Question.BDAY -> "Год моего рождения должен содержать только цифры"
    Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
    Question.IDLE -> //игнорировать валидацию
*/

    private fun startOver(): Pair<String, Triple<Int, Int, Int>> {
        question = Question.NAME
        return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
    }

    private fun repeatQuestion() : Pair<String, Triple<Int, Int, Int>> =
        "Это неправильный ответ\n${question.question}" to status.color

    private fun correctAnswer() : Pair<String, Triple<Int, Int, Int>> {
        question = question.nextQuestion()
        return "Отлично - ты справился!\n${question.question}" to status.color
    }

    fun listenAnswer(answer: String) : Pair<String, Triple<Int, Int, Int>> =
        if (question.answers.contains(answer)) {
            correctAnswer()
        } else {
            status = status.nextStatus()
            if (status == Status.NORMAL) startOver()
            else repeatQuestion()
        }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf<String>("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf<String>("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf<String>("метал", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf<String>("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf<String>("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом всё, вопросов больше нет.", listOf<String>()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion() : Question
    }
}