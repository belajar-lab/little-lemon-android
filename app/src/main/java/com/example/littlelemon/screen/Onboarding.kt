package com.example.littlelemon.screen

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Onboarding(sharedPreferences: SharedPreferences, navController: NavController) {
    var firstName by rememberSaveable {
        mutableStateOf("")
    }
    var lastName by rememberSaveable {
        mutableStateOf("")
    }
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var errorDialog by remember {
        mutableStateOf(false)
    }
    var confirmDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.fillMaxWidth(.5f)
        )
        Surface(modifier = Modifier.fillMaxWidth(), color = Color.Blue) {
            Text(
                text = "Let's get to know you",
                modifier = Modifier.padding(16.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Column(modifier = Modifier.fillMaxHeight(.75f)) {
            Text(
                text = "Personal information",
                modifier = Modifier.padding(vertical = 24.dp),
                style = MaterialTheme.typography.titleLarge
            )
            InputField(label = "First name", value = firstName, onValueChange = { firstName = it })
            InputField(label = "Last name", value = lastName, onValueChange = { lastName = it })
            InputField(label = "Email", value = email, onValueChange = { email = it })
        }
        Button(onClick = {
            if (firstName.isBlank() or lastName.isBlank() or email.isBlank()) {
                errorDialog = true
            } else {
                confirmDialog = true
            }
        }) {
            Text(text = "Register")
        }

        if (errorDialog) {
            AlertDialog(
                onDismissRequest = { errorDialog = false },
                confirmButton = {
                    TextButton(onClick = { errorDialog = false }) {
                        Text(text = "OK")
                    }
                },
                title = { Text(text = "Registration unsuccessful") },
                text = { Text(text = "Please enter all data") }
            )
        }
        if (confirmDialog) {
            AlertDialog(
                onDismissRequest = { confirmDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        confirmDialog = false
                        sharedPreferences.edit()
                            .putString("firstName", firstName)
                            .putString("lastName", lastName)
                            .putString("email", email)
                            .putBoolean("isLoggedIn", true)
                            .apply()
                        navController.navigate(Home.route)
                    }) {
                        Text(text = "Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { confirmDialog = false }) {
                        Text(text = "Cancel")
                    }
                },
                title = { Text(text = "Registration successful!") },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label)
        OutlinedTextField(value = value, onValueChange = onValueChange)
    }
}