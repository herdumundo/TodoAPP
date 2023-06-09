package com.cursokotlin.todoapp.addtasks.data

import androidx.room.PrimaryKey

data class TaskEntity(
    @PrimaryKey()
    val id:Int,
    val task:String,
    var selected:Boolean=false
)