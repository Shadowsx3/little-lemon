package com.example.littlelemon.composables

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.utils.validateRegData
import com.example.littlelemon.navigation.Onboarding
import com.example.littlelemon.navigation.Home
import com.example.littlelemon.ui.theme.HighlightGray
import com.example.littlelemon.ui.theme.LittleLemonTheme
import com.example.littlelemon.ui.theme.PrimaryGreen
import kotlinx.coroutines.launch

@Composable
fun Onboarding(sharedPreferences: SharedPreferences, navHostController: NavHostController) {
    val context = LocalContext.current
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)

    LaunchedEffect(key1 = keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    Box(modifier = Modifier.imePadding()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Spacer(modifier = Modifier.size(5.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "LittleLemon Logo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            )

            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.titleMedium,
                color = HighlightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryGreen, RectangleShape)
                    .padding(vertical = 30.dp)
            )

            Text(
                text = "Personal Information",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .padding(vertical = 40.dp),
                style = MaterialTheme.typography.titleSmall
            )


            FormTextField(firstName, "First name", "Manuel")
            FormTextField(lastName, "Last name", "Buslon")
            FormTextField(email, "Email", "mbulson@gmail.com")

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (validateRegData(firstName.value, lastName.value, email.value)) {
                        sharedPreferences.edit()
                            .putString("firstName", firstName.value)
                            .putString("lastName", lastName.value)
                            .putString("email", email.value)
                            .putBoolean("logged", true)
                            .apply()

                        navHostController.navigate(Home.route) {
                            popUpTo(Onboarding.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Invalid Details, Please try again",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Register")
            }
        }
    }
}

@Composable
fun FormTextField(
    value: MutableState<String>,
    label: String,
    placeholder: String
) {
    OutlinedTextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = { Text(text = label) },
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryGreen,
            focusedLabelColor = PrimaryGreen,
        ),
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    )
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        Onboarding(
            sharedPreferences = LocalContext.current.getSharedPreferences(
                "LittleLemon",
                Context.MODE_PRIVATE
            ), rememberNavController()
        )
    }
}