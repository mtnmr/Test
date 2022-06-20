package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.hamcrest.Matchers.`is`
import org.junit.Before

@Config(sdk = [31])
//@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    private lateinit var tasksRepository: FakeTestRepository

    private lateinit var tasksViewModel: TasksViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        tasksRepository = FakeTestRepository()
        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        tasksRepository.addTasks(task1, task2, task3)

        tasksViewModel = TasksViewModel(tasksRepository)
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {

        tasksViewModel.addNewTask()

        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {

        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }

}