package com.ezetap.ezetapassignmentappmodule.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ezetap.ezetapassignmentappmodule.R
import com.ezetap.ezetapassignmentnetworkmodule.model.UiType

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun FormReviewScreen(
    formViewModel: FormViewModel,
    onNavigateBack: () -> Unit
) {

    val uiStateCustomUi by formViewModel.uiState.collectAsStateWithLifecycle()

    // UI Data List
    val listUiData = remember {
        uiStateCustomUi.dataOrNull()?.listUiData?.filter {
            //Only label or Text Field UI type should be a part of the list
            it.getUiTypeEnum == UiType.Label || it.getUiTypeEnum == UiType.TextField
        } ?: emptyList()
    }

    Column(modifier = Modifier.padding(32.dp)) {

        // Back Icon
        Icon(
            modifier = Modifier.clickable {
                onNavigateBack.invoke()
            },
            painter = painterResource(id = R.drawable.ic_back), contentDescription = stringResource(
                id = R.string.icon_navigate_back
            )
        )

        // Title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = stringResource(id = R.string.review_screen_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )


        // Card Content
        ElevatedCard(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(vertical = 24.dp),
        ) {
            // List of UI Elements
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp, bottom = 20.dp, start = 24.dp, end = 24.dp)
            ) {
                items(listUiData.size) { index ->
                    val uiDataElement = listUiData[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        when (uiDataElement.getUiTypeEnum) {

                            // Title
                            UiType.Label -> {
                                Text(
                                    modifier = Modifier.padding(top = 16.dp),
                                    text = uiDataElement.valueText,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            // Value
                            UiType.TextField -> {
                                Text(
                                    text = uiDataElement.valueText,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }

                            else -> {}
                        }


                    }
                }
            }
        }

    }

}