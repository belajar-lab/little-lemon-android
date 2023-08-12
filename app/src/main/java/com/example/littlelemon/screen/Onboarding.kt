package com.example.littlelemon.screen

import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.Home
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(sharedPreferences: SharedPreferences? = null, navController: NavController? = null) {
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
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Image(
                    painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
                    contentDescription = stringResource(id = R.string.little_lemon_logo),
                    modifier = Modifier.height(30.dp)
                )
            })
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Surface(modifier = Modifier.fillMaxWidth(), color = LittleLemonColor.green) {
                    Text(
                        text = "Let's get to know you",
                        modifier = Modifier.padding(vertical = 32.dp),
                        color = LittleLemonColor.cloud,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
                Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                    Text(
                        text = "Personal information",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    InputField(
                        label = "First name",
                        value = firstName,
                        onValueChange = { firstName = it })
                    InputField(
                        label = "Last name",
                        value = lastName,
                        onValueChange = { lastName = it })
                    InputField(label = "Email", value = email, onValueChange = { email = it })
                    Button(
                        onClick = {
                            if (firstName.isBlank() or lastName.isBlank() or email.isBlank()) {
                                errorDialog = true
                            } else {
                                confirmDialog = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 32.dp),
                        border = BorderStroke(1.dp, SolidColor(Color(0xFFFFA500))),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = LittleLemonColor.charcoal,
                            containerColor = LittleLemonColor.yellow
                        )
                    ) {
                        Text(text = "Register", style = MaterialTheme.typography.labelLarge)
                    }
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
                                sharedPreferences?.edit()?.putString("firstName", firstName)
                                    ?.putString("lastName", lastName)?.putString("email", email)
                                    ?.putBoolean("isLoggedIn", true)?.apply()
                                navController?.navigate(Home.route)
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
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(value = value, onValueChange = onValueChange,
            )
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    Onboarding()
}