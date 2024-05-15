package com.example.littlelemon.composables

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.R
import com.example.littlelemon.navigation.Home
import com.example.littlelemon.navigation.Onboarding
import com.example.littlelemon.ui.theme.HighlightGray
import com.example.littlelemon.ui.theme.PrimaryGreen

@Composable
fun Profile(sharedPreferences: SharedPreferences, navHostController: NavHostController) {
    val firstName by remember { mutableStateOf(sharedPreferences.getString("firstName", "") ?: "") }
    val lastName by remember { mutableStateOf(sharedPreferences.getString("lastName", "") ?: "") }
    val email by remember { mutableStateOf(sharedPreferences.getString("email", "") ?: "") }

    val scrollState = rememberScrollState()


    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
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
            text = "Personal Information",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall
        )

        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = firstName,
            onValueChange = {},
            label = { Text(text = "First Name") },
            colors = TextFieldDefaults.colors(
                disabledContainerColor = HighlightGray,
                disabledLabelColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = lastName,
            onValueChange = {},
            label = { Text(text = "Last Name") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = HighlightGray,
                disabledLabelColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            enabled = false,
            readOnly = true,
            value = email,
            onValueChange = {},
            label = { Text("Email") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = HighlightGray,
                disabledLabelColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            shape = MaterialTheme.shapes.medium,
            onClick = {
                sharedPreferences.edit()
                    .clear()
                    .apply()

                navHostController.navigate(Onboarding.route) {
                    popUpTo(Home.route) { inclusive = true }
                    launchSingleTop = true
                }

            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Log Out")
        }
    }
}