package com.coursecampus.coursecampus.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coursecampus.coursecampus.domain.model.User
import com.coursecampus.coursecampus.domain.repository.AuthRepository
import com.coursecampus.coursecampus.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadExistingProfile()
    }

    private fun loadExistingProfile() {
        val uid = authRepository.getCurrentUserUid() ?: return
        viewModelScope.launch {
            val user = userRepository.getUserProfile(uid)
            user?.let {
                _uiState.value = ProfileUiState(
                    name = it.name,
                    gender = it.gender,
                    address = it.location,
                    category = it.category,
                    highestQualification = it.qualification,
                    yearOfPassing = it.yearOfPassing,
                    universityName = it.university,
                    courseName = it.course,
                    marks = it.cgpa,
                    interestedField = it.interest,
                    goals = it.goals
                )
            }
        }
    }

    fun onNameChange(newValue: String) { _uiState.value = _uiState.value.copy(name = newValue) }
    fun onGenderChange(newValue: String) { _uiState.value = _uiState.value.copy(gender = newValue) }
    fun onAddressChange(newValue: String) { _uiState.value = _uiState.value.copy(address = newValue) }
    fun onCategoryChange(newValue: String) { _uiState.value = _uiState.value.copy(category = newValue) }
    fun onQualificationChange(newValue: String) { _uiState.value = _uiState.value.copy(highestQualification = newValue) }
    fun onYearChange(newValue: String) { _uiState.value = _uiState.value.copy(yearOfPassing = newValue) }
    fun onUniversityChange(newValue: String) { _uiState.value = _uiState.value.copy(universityName = newValue) }
    fun onCourseChange(newValue: String) { _uiState.value = _uiState.value.copy(courseName = newValue) }
    fun onMarksChange(newValue: String) { _uiState.value = _uiState.value.copy(marks = newValue) }
    fun onInterestChange(newValue: String) { _uiState.value = _uiState.value.copy(interestedField = newValue) }
    fun onGoalsChange(newValue: String) { _uiState.value = _uiState.value.copy(goals = newValue) }

    fun saveProfile(onSuccess: () -> Unit) {
        val uid = authRepository.getCurrentUserUid() ?: return
        val email = authRepository.getCurrentUserEmail() ?: ""
        val state = _uiState.value
        val user = User(
            uid = uid,
            name = state.name,
            email = email,
            gender = state.gender,
            location = state.address,
            category = state.category,
            qualification = state.highestQualification,
            yearOfPassing = state.yearOfPassing,
            university = state.universityName,
            course = state.courseName,
            cgpa = state.marks,
            interest = state.interestedField,
            goals = state.goals
        )
        viewModelScope.launch {
            val success = userRepository.saveUserProfile(user)
            if (success) onSuccess()
        }
    }
}

data class ProfileUiState(
    val name: String = "",
    val gender: String = "Other",
    val address: String = "",
    val category: String = "",
    val highestQualification: String = "",
    val yearOfPassing: String = "",
    val universityName: String = "",
    val courseName: String = "",
    val marks: String = "",
    val interestedField: String = "",
    val goals: String = ""
) {
    fun isValid(): Boolean {
        return name.isNotBlank() &&
                gender.isNotBlank() &&
                address.isNotBlank() &&
                category.isNotBlank() &&
                highestQualification.isNotBlank() &&
                yearOfPassing.isNotBlank() &&
                universityName.isNotBlank() &&
                courseName.isNotBlank() &&
                marks.isNotBlank() &&
                interestedField.isNotBlank() &&
                goals.isNotBlank()
    }
}
