package com.ezetap.ezetapassignmentappmodule.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ezetap.ezetapassignmentappmodule.R

@Composable
fun LogoImage(modifier: Modifier = Modifier, imageUrl: String) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(data = imageUrl)
            .crossfade(enable = true)
            .build(),
        contentDescription = stringResource(id = R.string.app_name)
    )
}

/**
 * Label
 */
@Composable
fun LabelView(modifier: Modifier, valueText: String) {
    Text(modifier = modifier, text = valueText, color = MaterialTheme.colorScheme.secondary)
}

/**
 * Input Text Field View
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextFieldView(
    modifier: Modifier,
    hint: String,
    valueText: String,
    onInputTextChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next
) {
    var textInputString by remember {
        mutableStateOf(value = valueText)
    }

    // Outlined Text Field
    OutlinedTextField(
        modifier = modifier,
        value = textInputString,
        onValueChange = {
            textInputString = it
            onInputTextChange(it)
        },
        placeholder = {
            Text(text = hint, color = MaterialTheme.colorScheme.outline)
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction)
    )
}

/**
 * Button View
 */
@Composable
fun ButtonView(
    modifier: Modifier = Modifier,
    valueText: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.height(48.dp)
        ) {
            Text(text = valueText, style = MaterialTheme.typography.titleMedium)
        }
    }

}

/**
 * Full Screen Circle Loading
 */
@Composable
fun FullScreenCircularLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp))
    }
}

/**
 * Error View
 */
@Composable
fun ErrorView(
    errorText: String = stringResource(id = R.string.msg_unknown_error)
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Icon
        Icon(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = stringResource(R.string.icon_error)
        )

        // Text Content
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = errorText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}



