package com.ezetap.ezetapassignmentappmodule.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ezetap.ezetapassignmentappmodule.ui.screens.FormReviewScreen
import com.ezetap.ezetapassignmentappmodule.ui.screens.FormScreen
import com.ezetap.ezetapassignmentappmodule.ui.screens.FormViewModel
import com.ezetap.ezetapassignmentappmodule.ui.screens.ScreenRoutes
import com.ezetap.ezetapassignmentappmodule.ui.theme.EzetapAssignmentAppModuleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EzetapAssignmentAppModuleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    // App Nav Host
                    MyAppNavHost()
                }
            }
        }
    }
}

@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ScreenRoutes.FORM_SCREEN
) {
    // Form ViewModel initialize
    val formViewModel: FormViewModel = viewModel()

    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {

        // Form Screen
        composable(
            route = ScreenRoutes.FORM_SCREEN
        ) {
            FormScreen(
                formViewModel = formViewModel,
                onNavigateToReview = {
                    // Navigate to Review Screen
                    navController.navigate(ScreenRoutes.FORM_REVIEW_SCREEN)
                }
            )
        }

        // Form Review Screen
        composable(
            route = ScreenRoutes.FORM_REVIEW_SCREEN
        ) {
            FormReviewScreen(
                formViewModel = formViewModel,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EzetapAssignmentAppModuleTheme {

    }
}