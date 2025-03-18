package com.dluche.myspeedrunners.ui.feature.runnerdetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunnerDetailsViewModel @Inject constructor(
    private val repository: RunnersRepository
) : ViewModel() {

    private val _runnerState = MutableStateFlow<RunnerDto?>(null)
    val runnerState = _runnerState.asStateFlow()


    init {
        val id = "kjp1v74j"
        fetchRunner(id)
    }

    private fun fetchRunner(runnerId: String) {
        viewModelScope.launch {
            repository.getRunner(runnerId)
                .fold(
                    onSuccess = { runner ->
                        _runnerState.update {
                            runner
                        }
                    },
                    onFailure = { error ->
                        // Trata o erro
                        Log.e("RunnerDetailsViewModel", "Erro ao buscar runner", error)
                    }
                )
        }
    }
}