package com.example.practivemvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practivemvvm.model.DataModelClass
import com.example.practivemvvm.model.Photos
import com.example.practivemvvm.network.Response
import com.example.practivemvvm.repository.UserRepository
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private var actualData = ArrayList<DataModelClass>()

    private val photos = MutableStateFlow(ArrayList<DataModelClass>())
    val _photos: StateFlow<ArrayList<DataModelClass>> = photos.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    fun getData() {
        viewModelScope.launch {
            val data = userRepository.getUserProfileData()
            if (data is Response.Success) {
                actualData = data.data
                photos.update { data.data }
            } else if (data is Response.Failure) {
                _errorMessage.update { data.error}
            }
        }
    }

    // Filter function based on language
    fun filterByLanguage(language: String) {
       if (language == ProgrammingLanguage.ALL.languageText) {
           photos.value = actualData
       } else {
           photos.value = actualData.filter { it.language?.lowercase() == language.lowercase() } as ArrayList<DataModelClass>
       }
    }

    enum class ProgrammingLanguage(val languageText: String) {
        ALL("All"),
        JAVA("Java"),
        KOTLIN("Kotlin")
    }
}