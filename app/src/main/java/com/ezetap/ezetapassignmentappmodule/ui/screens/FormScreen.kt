package com.ezetap.ezetapassignmentappmodule.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ezetap.ezetapassignmentappmodule.ui.common.*
import com.ezetap.ezetapassignmentappmodule.ui.generic.UiState
import com.ezetap.ezetapassignmentnetworkmodule.model.ApiResponseModel
import com.ezetap.ezetapassignmentnetworkmodule.model.UiType

/**
 * Main Form Screen containing the UI views
 */
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun FormScreen(
    formViewModel: FormViewModel,
    onNavigateToReview: () -> Unit
) {

    val uiStateCustomUi by remember {
        formViewModel.uiState
    }.collectAsStateWithLifecycle()

    when (uiStateCustomUi) {
        // Success State
        is UiState.Success -> {
            FormInputsView(
                apiResponseModel = (uiStateCustomUi as UiState.Success<ApiResponseModel>).data,
                onInputValueUpdated = { indexToUpdate, updatedValueText ->
                    formViewModel.updateUiDataList(
                        indexToUpdate = indexToUpdate,
                        updatedValueText = updatedValueText
                    )
                },
                onSubmit = {
                    onNavigateToReview.invoke()
                })
        }

        // Failure State
        is UiState.Failure -> {
            val stringResId =
                getRelevantFailureErrorMessage(failureCause = (uiStateCustomUi as UiState.Failure).failure)
            ErrorView(errorText = stringResource(id = stringResId))
        }

        // Loading State
        is UiState.Loading -> {
            FullScreenCircularLoading()
        }
    }

}

@Composable
fun FormInputsView(
    apiResponseModel: ApiResponseModel,
    onInputValueUpdated: (indexToUpdate: Int, updatedValueText: String) -> Unit,
    onSubmit: () -> Unit
) {
    Log.d("FormInputsView","Called")
    val uiDataList = apiResponseModel.listUiData ?: emptyList()

    // UI Content
    Column(
        modifier = Modifier.padding(all = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Ezetap Logo
        LogoImage(
            modifier = Modifier.size(96.dp),
            imageUrl = apiResponseModel.logoUrl
        )

        // Heading
        Text(
            text = apiResponseModel.headingText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )

        // Card Content
        ElevatedCard(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(top = 24.dp),
        ) {
            // List of UI Elements
            LazyColumn(
                modifier = Modifier.padding(24.dp)
            ) {

                // Items iteration
                items(uiDataList.size) { index ->
                    // UI element based on item
                    val uiDataElement = uiDataList[index]
                    when (uiDataElement.getUiTypeEnum) {

                        // Label
                        UiType.Label -> {

                            // Label View
                            LabelView(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .fillMaxWidth(),
                                valueText = uiDataElement.valueText
                            )
                        }

                        // Text
                        UiType.TextField -> {

                            // Input Field View
                            InputTextFieldView(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .fillMaxWidth(),
                                hint = uiDataElement.hintText,
                                valueText = uiDataElement.valueText,
                                onInputTextChange = { updatedInputTextValue ->
                                    //Invoke function
                                    onInputValueUpdated.invoke(index, updatedInputTextValue)
                                }
                            )
                        }

                        // Button
                        UiType.Button -> {
                            ButtonView(
                                modifier = Modifier
                                    .padding(top = 40.dp)
                                    .fillMaxSize(),
                                valueText = uiDataElement.valueText,
                                onClick = onSubmit
                            )
                        }

                        // Unrecognized View
                        UiType.Unrecognized -> {

                        }
                    }
                }
            }
        }


    }


}

