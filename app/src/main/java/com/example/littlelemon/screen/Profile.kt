package com.example.littlelemon.screen

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemon.Onboarding
import com.example.littlelemon.R

@Composable
fun Profile(sharedPreferences: SharedPreferences, navController: NavController) {
    val firstName = sharedPreferences.getString("firstName", "")!!
    val lastName = sharedPreferences.getString("lastName", "")!!
    val email = sharedPreferences.getString("email", "")!!

    Column {
        Image(
            painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.fillMaxWidth(.5f)
        )
        Column {
            Text(
                text = "Personal information",
                modifier = Modifier.padding(vertical = 24.dp),
                style = MaterialTheme.typography.titleLarge
            )
            ItemField(title = "First name", text = firstName)
            ItemField(title = "Last name", text = lastName)
            ItemField(title = "Email", text = email)
        }
        Button(onClick = {
            sharedPreferences.edit().clear().apply()
            navController.navigate(Onboarding.route)
        }) {
            Text(text = "Log out")
        }
    }
}

@Composable
fun ItemField(title: String, text: String) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 24.dp)) {
        Text(text = title)
        OutlinedTextField(value = text, onValueChange = { /*Read Only*/ }, readOnly = true)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfilePreview() {
//    Profile()
//}