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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState = _userState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val uid = authRepository.getCurrentUserUid() ?: return
        viewModelScope.launch {
            _isLoading.value = true
            val user = userRepository.getUserProfile(uid)
            _userState.value = user ?: User(uid = uid, email = authRepository.getCurrentUserEmail() ?: "")
            _isLoading.value = false
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
