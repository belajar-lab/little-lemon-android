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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.Home
import com.example.littlelemon.Onboarding
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(sharedPreferences: SharedPreferences? = null, navController: NavController? = null) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
                        contentDescription = stringResource(id = R.string.little_lemon_logo),
                        modifier = Modifier.height(30.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigate(Home.route) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.button_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val firstName = sharedPreferences?.getString("firstName", "") ?: ""
            val lastName = sharedPreferences?.getString("lastName", "") ?: ""
            val email = sharedPreferences?.getString("email", "") ?: ""
            item {
                Column(modifier = Modifier.width(IntrinsicSize.Max)) {
                    Text(
                        text = "Personal information",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                    ItemField(title = "First name", text = firstName)
                    ItemField(title = "Last name", text = lastName)
                    ItemField(title = "Email", text = email)
                    Button(
                        onClick = {
                            sharedPreferences?.edit()?.clear()?.apply()
                            navController?.navigate(Onboarding.route)
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
                        Text(text = "Log out", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemField(title: String, text: String) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        OutlinedTextField(
            value = text,
            onValueChange = { /*Read Only*/ },
            enabled = false,
            readOnly = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile()
}