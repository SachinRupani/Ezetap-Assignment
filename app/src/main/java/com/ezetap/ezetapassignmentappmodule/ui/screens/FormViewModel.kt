package com.ezetap.ezetapassignmentappmodule.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezetap.ezetapassignmentappmodule.ui.generic.UiState
import com.ezetap.ezetapassignmentnetworkmodule.ApiService
import com.ezetap.ezetapassignmentnetworkmodule.generic.ApiResult
import com.ezetap.ezetapassignmentnetworkmodule.model.ApiResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FormViewModel : ViewModel() {

    /**
     * [ApiService] instance
     */
    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    init {
        fetchCustomUiJson()
    }


    private val _uiState: MutableStateFlow<UiState<ApiResponseModel>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ApiResponseModel>>
        get() = _uiState

    /**
     * Function to get the [UiState] of [ApiResponseModel]
     */
    private fun fetchCustomUiJson() {
        viewModelScope.launch {
            when (val result =
                apiService.fetchCustomUi(apiUrl = "https://demo.ezetap.com/mobileapps/android_assignment.json")) {

                // API Success
                is ApiResult.Success -> {
                    _uiState.value = UiState.Success(data = result.data)
                }

                // API Failure
                is ApiResult.Failure -> {
                    _uiState.value = UiState.Failure(failure = result.failureCause)
                }
            }
        }
    }

    /**
     * Function to update the UI data list
     * on change of input value
     */
    fun updateUiDataList(indexToUpdate: Int, updatedValueText: String) {
        viewModelScope.launch(Dispatchers.IO) {

            // Current API Response
            val currentApiResponseModel = _uiState.value.dataOrNull()

            // Current UI Data List
            val currentListUiData = currentApiResponseModel?.listUiData ?: emptyList()

            if (currentListUiData.isNotEmpty()) {

                val currentItem = currentListUiData[indexToUpdate]

                // Updated Item
                val updatedItem = currentItem.copy(
                    uiType = currentItem.uiType,
                    hintText = currentItem.hintText,
                    key = currentItem.key,
                    valueText = updatedValueText
                )

                val updatedUiDataList = currentListUiData.toMutableList()
                updatedUiDataList[indexToUpdate] = updatedItem

                // Update List
                currentApiResponseModel?.let {
                    _uiState.value = UiState.Success(
                        it.copy(
                            logoUrl = it.logoUrl,
                            headingText = it.headingText,
                            listUiData = updatedUiDataList.toList()
                        )
                    )
                }

            }
        }
    }
}

