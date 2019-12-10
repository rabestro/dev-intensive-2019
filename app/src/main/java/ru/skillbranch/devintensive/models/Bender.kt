package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion() : String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String) : Pair<String, Triple<Int, Int, Int>> =
        if (question.answers.contains(answer)) {
            question = question.nextQuestion()
            "Олично! Это правильный ответ!\n${question.question}" to status.color
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
        NAME("Как меня зовут?", listOf<String>("бендер", "bender")) {
            override fun nextQuestion(): Question {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        },
        PROFESSION("Назови мою профессию?", listOf<String>("сгибальщик", "bender")) {
            override fun nextQuestion(): Question {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        },
        MATERIAL("Из чего я сделан?", listOf<String>("метал", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        },
        BDAY("Когда меня создали?", listOf<String>("2993")) {
            override fun nextQuestion(): Question {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        },
        SERIAL("Мой серийный номер?", listOf<String>("2716057")) {
            override fun nextQuestion(): Question {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        },
        IDLE("На этом всё, вопросов больше нет.", listOf<String>()) {
            override fun nextQuestion(): Question {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        };

        abstract fun nextQuestion() : Question
    }
}