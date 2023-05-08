package com.cursokotlin.todoapp.addtasks.data

import com.cursokotlin.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private  val taskDao:TaskDao){

   // val tasks:Flow<List<TaskEntity>> = taskDao.getTasks()
    val tasks: Flow<List<TaskModel>> =taskDao.getTasks().map {  items-> items.map { TaskModel(it.id,it.task,it.selected) }}
    suspend fun add(taskModel: TaskModel){

        taskDao.AddTask(TaskEntity(taskModel.id, taskModel.task,taskModel.selected))
    }
}