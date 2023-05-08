package com.cursokotlin.todoapp.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.todoapp.addtasks.domain.AddTaskUseCase
import com.cursokotlin.todoapp.addtasks.domain.GetTasksUseCase
import com.cursokotlin.todoapp.addtasks.ui.model.TaskModel
import com.cursokotlin.todoapp.addtasks.ui.model.TaskUiState
import com.cursokotlin.todoapp.addtasks.ui.model.TaskUiState.Success
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    getTasksUseCase: GetTasksUseCase
) :ViewModel(){

    val uiState:StateFlow<TaskUiState> = getTasksUseCase().map(::Success )
        .catch{Error(it)}
        .stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000),TaskUiState.Loading)


    private  val _showDialog= MutableLiveData<Boolean>()
    val showDialog : LiveData<Boolean> = _showDialog

    private  val _tasks= mutableStateListOf<TaskModel>()
    val tasks:List<TaskModel> =_tasks
    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTasksCreated(task: String) {
        _showDialog.value = false
        _tasks.add(TaskModel(task = task))

     }

    fun onShowDialogSelected() {
        _showDialog.value = true

    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
val index= _tasks.indexOf(taskModel)
        _tasks[index]=_tasks[index].let{
            it.copy(selected = !it.selected)
        }
     }

    fun onItemRemove(taskModel: TaskModel) {
        val task = _tasks.find { it.id == taskModel.id  }
        _tasks.remove(task)
    }


}