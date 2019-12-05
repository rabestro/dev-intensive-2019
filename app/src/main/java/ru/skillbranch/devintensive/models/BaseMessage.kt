package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id:String,
    val from: String,
    val chat: Chat,
    val isIncomming: Boolean = false,
    val date: Date = Date()
)
{
    abstract fun formatMessage() : String
}